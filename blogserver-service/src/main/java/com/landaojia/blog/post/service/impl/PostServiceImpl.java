package com.landaojia.blog.post.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.base.Strings;
import com.landaojia.blog.common.dao.BaseEntity;
import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.redis.RedisService;
import com.landaojia.blog.common.util.DateUtil;
import com.landaojia.blog.common.util.FileHelper;
import com.landaojia.blog.post.dao.PostDao;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.entity.PostAttachment;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.blog.tag.service.TagService;
import com.landaojia.blog.user.entity.User;

@Service
public class PostServiceImpl implements PostService {

    private static final String REDIS_KEY = "viewers_of_post_";

    @Resource
    private CommonDao commonDao;

    @Resource
    private PostDao postDao;

    @Resource
    private RedisService redisService;

    @Resource
    private TagService tagService;
    
    @Resource
    private FileHelper fileHelper;

    @Transactional
    @Override
    public Long create(Post post, User user) {
        post.setUserId(user.getId());
        post.setViewCount(0L);
        post.setCreatedBy(user.getEmail());
        post.setUpdatedBy(user.getEmail());
        post.setIsPrivate((null != post.getIsPrivate() && post.getIsPrivate()) ? true : false);
        Long id = this.commonDao.insert(post).getId();
        this.tagService.saveTags(post.getTagList(), post.getId());
        return id;
    }

    @Override
    public Post queryById(Long id, User user) {
        Long userId = null == user ? null : user.getId();
        return this.postDao.selectOne(id, userId);
    }

    @Override
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        PageList<Post> pageList = (PageList<Post>) this.postDao.searchByPage(new Post(), new PageBounds(page,
                limit, Order.formString("createdDate.desc")));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("posts", new ArrayList<Post>(pageList));
        map.put("pageInfo", pageList.getPaginator());
        map.put("popularTags", this.tagService.searchPopularTags());
        return map;
    }

    @Override
    public Map<String, Object> queryByUserId(Integer page, Integer limit, Long userId) {
        Post cond = new Post();
        cond.setUserId(userId);
        PageList<Post> pageList = (PageList<Post>) this.postDao.searchByPage(cond, new PageBounds(page,
                limit, Order.formString("createdDate.desc")));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("posts", new ArrayList<Post>(pageList));
        map.put("pageInfo", pageList.getPaginator());
        return map;
    }

    @Transactional
    @Override
    public void update(Long id, Post post, User user) {
        Post oldPost = this.postDao.selectOne(id, user.getId());
        if (null == oldPost) {
            throw new CommonException(CommonExceptionCode.POST_NOT_EXISTS);
        }
        if (!Strings.isNullOrEmpty(post.getTitle())) {
            oldPost.setTitle(post.getTitle());
        }
        if (!Strings.isNullOrEmpty(post.getContent())) {
            oldPost.setContent(post.getContent());
        }
        oldPost.setIsPrivate((null != post.getIsPrivate() && post.getIsPrivate()) ? true : false);
        oldPost.setUpdatedBy(user.getEmail());
        this.commonDao.update(oldPost);
        this.tagService.updateTags(oldPost.getTagList(), post.getTagList(), id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.commonDao.removeById(Post.class, id);
        this.tagService.deleteByPostId(id);
    }

    @Transactional
    @Override
    public void addViewCount(String ip, Long id) {
        String key = REDIS_KEY + id;
        if (!this.redisService.exists(key)) {
            this.redisService.sadd(key, ip);
            this.redisService.expire(key, DateUtil.getRemainingSeconds());
            this.postDao.addViewCount(id);
        } else if (!this.redisService.sismember(key, ip)) {
            this.redisService.sadd(key, ip);
            this.postDao.addViewCount(id);
        }
    }

    @Override
    public Map<String, Object> search(Integer page, Integer limit, String by, String q) {
        PageList<Post> pageList = null;
        if (by.equals("tag")) {
            pageList = (PageList<Post>) this.postDao.searchByTag(q,
                    new PageBounds(page, limit, Order.formString("createdDate.desc")));
        } else {
            Post cond = new Post();
            if (by.equals("title")) {
                cond.setTitle(q);
            } else {
                cond.setContent(q);
            }
            pageList = (PageList<Post>) this.postDao.searchByPage(cond,
                    new PageBounds(page, limit, Order.formString("createdDate.desc")));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("posts", new ArrayList<Post>(pageList));
        map.put("pageInfo", pageList.getPaginator());
        return map;
    }

	@Override
	@Transactional
	public String addAttachment(MultipartFile file, Long postId, User currentUser, String webRootPath){
		if(Strings.isNullOrEmpty(webRootPath)) throw new CommonException(CommonExceptionCode.E999999);
		Post post = commonDao.findById(Post.class, postId);
		if(post == null) throw new CommonException(CommonExceptionCode.POST_NOT_EXISTS);
		if(post.getUserId().compareTo(currentUser.getId()) != 0) throw new CommonException(CommonExceptionCode.POST_ATTACHMENT_NO_RIGHT_UPLOAD);
		String oldName = file.getOriginalFilename();
		String postFix = oldName.split("\\.")[1];
		Long time = DateUtil.getCurrentDate().getTime();
		String newName = postId+""+time+"."+postFix;
		try {
			fileHelper.saveFile(file, newName, webRootPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommonException(CommonExceptionCode.POST_ATTACHMENT_UPLOAD_FAIL);
		}
		PostAttachment pa = new PostAttachment();
		pa.setFileType(PostAttachment.getFileType(postFix));
		pa.setFileSize(file.getSize());
		pa.setFileOriginalName(oldName);
		pa.setFileName(newName);
		pa.setPostId(post.getId());
		pa.setDeletedFlag(BaseEntity.DELETED_FLAG_NO);
		pa.setCreatedDate(new Date(time));
		pa.setUpdatedDate(new Date(time));
		pa.setCreatedBy(currentUser.getUserName());
		pa.setUpdatedBy(currentUser.getUserName());
		commonDao.insert(pa);
		return fileHelper.getFileDir()+pa.getFileName();
	}

}
