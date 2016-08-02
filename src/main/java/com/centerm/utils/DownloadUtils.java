package com.centerm.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DownloadUtils {
	public static void downLoadFile(String filePath,
			HttpServletResponse response) throws Exception {
		File f = new File(filePath);
		if (!f.exists()) {
			throw new Exception("文件不存在");
		}
		BufferedInputStream br = null;
		OutputStream out = null;
		try {
			br = new BufferedInputStream(new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;

			String name = filePath.substring(filePath.lastIndexOf("/") + 1);
			response.reset();
			// response.setContentType("application/vnd.ms-excel");
			response.setContentType("application/octet-stream; charset=gbk");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String( name.getBytes("gb2312"), "ISO8859-1" ));
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "public");
			out = response.getOutputStream();
			while ((len = br.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
		} finally {
			if (br != null) {
				br.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	
	public static void downLoadExcel(HSSFWorkbook workbook, String fileName,
			HttpServletResponse response) throws Exception {
		if (workbook == null) {
			throw new Exception("文件不存在");
		}
		OutputStream out = null;
		try {
			if (!fileName.endsWith(".xls")) {
				fileName = fileName + ".xls";
			}
			response.reset();
			// response.setContentType("application/vnd.ms-excel");
			response.setContentType("application/octet-stream; charset=gbk");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1" ));
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "public");
			out = response.getOutputStream();
			workbook.write(out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
