package com.YuanWai.tools.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.YuanWai.tools.VO.FileNameVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/ChangeFileName")
public class ChangeFileNameController {

	private static final Logger log = LoggerFactory.getLogger(ChangeFileNameController.class);

	private static final String REQUEST_INDEX = "";
	private static final String RESPONSE_INDEX = "/FolderFormat";

	@RequestMapping(value = REQUEST_INDEX, method = { RequestMethod.GET, RequestMethod.POST })
	public String changeFileName(Model model, FileNameVO vo) throws IOException {
		log.info("Enter Index Page");
		object2Json(vo); /* 前端傳來的資訊 */
		if(!StringUtils.isEmpty(vo.getFolderUrl())) {
			File f = new File(vo.getFolderUrl());
			foundFile(f, vo);
		}
		model.addAttribute("vo", vo);
		return RESPONSE_INDEX;
	}

	public void foundFile(File f, FileNameVO vo) throws IOException {
		if (f.listFiles() != null) {
			for (final File fileEntry : f.listFiles()) {
				StringBuilder newFileName = new StringBuilder();
				StringBuilder newFilePath = new StringBuilder();
				if (fileEntry.exists()) {
					if (fileEntry.isDirectory()) {/* 資料夾 */
						if (fileEntry.getName().toUpperCase().contains(vo.getFileName())) {
							log.info("取得資料夾名稱 : {}", fileEntry.getName());
							newFilePath.setLength(0);
							newFilePath.append(vo.getFolderUrl()).append("\\")
									.append(vo.getFormatName());/* 重新命名後的檔案位置與檔案名稱 */
							boolean result = fileEntry.renameTo(new File(newFilePath.toString()));
							log.info("result = " + result);
//							Files.move(fileEntry.toPath(), fileEntry.toPath());
						}
						foundFile(new File(newFilePath.toString()), vo);
					} else {/* 檔案 */
						if (fileEntry.getParentFile().getName().contains(vo.getFileName())) {
							log.info("取得檔案名稱 : {}", fileEntry.getName());
							newFilePath.setLength(0);
							newFileName.setLength(0);
							if (fileEntry.getName().contains(".mp4")) {
								newFileName.append(vo.getFileName()).append(".mp4");
								changeName(newFilePath, newFileName, fileEntry, vo);
							}
							if (fileEntry.getName().contains(".jpg")) {
								newFileName.append(vo.getFileName()).append(".jpg");
								changeName(newFilePath, newFileName, fileEntry, vo);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 更改檔名
	 * 
	 * @param path
	 * @param name
	 * @param f
	 * @param vo
	 */
	public void changeName(StringBuilder path, StringBuilder name, File f, FileNameVO vo) {
		path.append(f.getParentFile()).append("\\").append(name);/* 重新命名後的檔案位置與檔案名稱 */
		f.renameTo(new File(path.toString()));
	}

	/**
	 * 列印參數
	 * 
	 * @param o
	 */
	public void object2Json(Object o) {
		String result = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(o);
			log.info("Object2Json : {}", result);
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException:{}", e);
		}
	}
}
