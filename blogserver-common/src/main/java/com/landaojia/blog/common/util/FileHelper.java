package com.landaojia.blog.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

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
		String realPath = dir.getPath() + File.separator + filename;
		File realFile = new File(realPath);
		if (!realFile.exists())
			realFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(realFile);
		byte[] b = new byte[1024];
		while ((stream.read(b)) != -1) {
			fos.write(b);
		}
		stream.close();
		fos.close();
	}

	public static String calculateFileSize(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
		if (returnValue > 1)
			return (returnValue + "MB");
		BigDecimal kilobyte = new BigDecimal(1024);
		returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
		return (returnValue + "KB");
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
