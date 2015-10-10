package com.landaojia.blog.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;

/**
 * @author Jason 2015年10月8日
 */
public class FileHelper {

	private String fileDir;

	private Long limitSize;

	public void saveFile(MultipartFile file, String filename, String webRootPath) throws IOException {
		if (file == null)
			throw new CommonException(CommonExceptionCode.POST_ATTACHMENT_UPLOAD_FAIL);
		if (file.getSize() > limitSize)
			throw new CommonException(CommonExceptionCode.POST_ATTACHMENT_OUT_OF_SIZE);
		InputStream stream = file.getInputStream();
		File dir = new File(webRootPath + fileDir);
		if (!dir.exists())
			dir.mkdirs();
		FileOutputStream fs = new FileOutputStream(dir.getPath() + filename);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, bytesum, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	public Float calculateFileSize(Long fileSize) {
		return new Float((Double) (Math.round(fileSize * 10000) / 1024 / 1024 / 10000.0));
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public Long getLimitSize() {
		return limitSize;
	}

	public void setLimitSize(Long limitSize) {
		this.limitSize = limitSize;
	}

}
