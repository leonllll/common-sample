package com.leonlu.code.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	/**
	 * 复制文件
	 * 
	 * @param srcFile
	 *            源文件File
	 * @param destFile
	 *            目标文件File
	 * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
	 */
	public static long copyFile(File srcFile, File destFile) {
		long copySizes = 0;
		if (!srcFile.exists()) {
			System.out.println("源文件不存在");
			copySizes = -1;
		} else if (destFile.getParentFile() == null
				|| !destFile.getParentFile().exists()) {
			System.out.println("目标目录不存在");
			copySizes = -1;
		} else {
			try {
				FileChannel fcin = new FileInputStream(srcFile).getChannel();
				FileChannel fcout = new FileOutputStream(destFile).getChannel();
				long size = fcin.size();
				fcin.transferTo(0, fcin.size(), fcout);
				fcin.close();
				fcout.close();
				copySizes = size;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return copySizes;
	}

	public static String readFile(File file) {
		System.out.println("readFile(File file)");
		BufferedReader bf = null;
		StringBuilder sb = new StringBuilder();
		try {
			bf = new BufferedReader(new FileReader(file));
			String content = "";
			while (content != null) {
				content = bf.readLine();
				if (content == null) {
					break;
				}
				sb.append(content);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (bf != null) {
					bf.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static String readFile(InputStream is){
		System.out.println("readFile(InputStream is)");
		BufferedReader bf = null;
		StringBuilder sb = new StringBuilder();
		try {
			bf = new BufferedReader(new InputStreamReader(is));
			String content = "";
			while (content != null) {
				content = bf.readLine();
				if (content == null) {
					break;
				}
				sb.append(content);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (bf != null) {
					bf.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
