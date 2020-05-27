package com.example.project_01.controller.admin.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.service.admin.file.ManageFileService;

@Controller
public class ManageFileController {
	@Value("${smarteditor.upload.directory}")
	String editorPath;
	@Autowired
	ManageFileService fileService;
	
	//스마트에디터에 이미지 업로드시 처리
	@ResponseBody
	@RequestMapping("/admin/smarteditor/fileupload")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		try { // 파일정보
			String sFileInfo = "";
			// 파일명을 받는다 - 일반 원본파일명
			String filename = request.getHeader("file-name");
			// 파일 확장자
			String filename_ext = filename.substring(filename.lastIndexOf(".") + 1);
			// 확장자를소문자로 변경
			filename_ext = filename_ext.toLowerCase();
			// 이미지 검증 배열변수
			String[] allow_file = { "jpg", "png", "bmp", "gif" };
			// 돌리면서 확장자가 이미지인지
			int cnt = 0;
			for (int i = 0; i < allow_file.length; i++) {
				if (filename_ext.equals(allow_file[i])) {
					cnt++;
				}
			}
			// 이미지가 아님
			if (cnt == 0) {
				PrintWriter print = response.getWriter();
				print.print("NOTALLOW_" + filename);  // -> attach_photo.js의 callAjaxForHTML5 함수에서 NOTALLOW_ 포함시 처리				print.flush();
				print.close();
			} else {
				// 이미지이므로 신규 파일로 디렉토리 설정 및 업로드
				File file = new File(editorPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String realFileNm = "";
				realFileNm = fileService.makeFileName(filename);
				String rlFileNm = editorPath + realFileNm;
				///////////////// 서버에 파일쓰기 /////////////////
				InputStream is = request.getInputStream();
				OutputStream os = new FileOutputStream(rlFileNm);
				int numRead;
				byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
				while ((numRead = is.read(b, 0, b.length)) != -1) {
					os.write(b, 0, numRead);
				}
				if (is != null) {
					is.close();
				}
				os.flush();
				os.close();

				///////////////// // 정보 출력
				sFileInfo += "&bNewLine=true";
				// img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
				sFileInfo += "&sFileName=" + filename;
				sFileInfo += "&sFileURL=" + editorPath + realFileNm;
				PrintWriter print = response.getWriter();
				print.print(sFileInfo);
				print.flush();
				print.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
