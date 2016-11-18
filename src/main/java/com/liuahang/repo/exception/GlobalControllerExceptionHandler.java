package com.liuahang.repo.exception;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
class GlobalControllerExceptionHandler {
    private Logger logger = Logger.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorMessage handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class, HttpMessageNotReadableException.class, JsonMappingException.class,
        ServletRequestBindingException.class, IllegalArgumentException.class})
    public @ResponseBody ErrorMessage handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.warn(e.getMessage());
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ FileNotFoundException.class })
    public @ResponseBody String handleFileNotFoundException(Exception e) {
        logger.warn(e.getMessage());
        //return new ErrorMessage(e.getLocalizedMessage());
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" +
"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
"<head>"+
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"/>"+
"<title>404 - 找不到文件或目录</title>"+
"<style type=\"text/css\">"+
"<!--"+
"body{margin:0;font-size:.7em;font-family:Verdana, Arial, Helvetica, sans-serif;background:#EEEEEE;}"+
"fieldset{padding:0 15px 10px 15px;} "+
"h1{font-size:2.4em;margin:0;color:#FFF;}"+
"h2{font-size:1.7em;margin:0;color:#CC0000;} "+
"h3{font-size:1.2em;margin:10px 0 0 0;color:#000000;} "+
"#header{width:96%;margin:0 0 0 0;padding:6px 2% 6px 2%;font-family:\"trebuchet MS\", Verdana, sans-serif;color:#FFF;"+
"background-color:#555555;}"+
"#content{margin:0 0 0 2%;position:relative;}"+
".content-container{background:#FFF;width:96%;margin-top:8px;padding:10px;position:relative;}"+
"-->"+
"</style>"+
"</head>"+
"<body>"+
"<div id=\"header\"><h1>404 - 文件不存在</h1></div>"+
"<div id=\"content\">"+
" <div class=\"content-container\"><fieldset>"+
"  <h3>404 - 找不到文件或目录，文件不存在或已被删除。</h3>"+
"  <p>"+
"<a style=\"color:#997c57\" href=\"javascript:history.back()\">返回</a>"+
"</p>"+
" </fieldset></div>"+
"</div>"+
"</body>"+
"</html>";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public @ResponseBody ErrorMessage handleHttpRequestMethodNotSupportedException(Exception e) {
        logger.warn(e.getMessage());
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    public @ResponseBody ErrorMessage handleIllegalStateException(Exception e) {
        logger.warn(e.getMessage());
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public @ResponseBody ErrorMessage handleMultipartException(MultipartException e) {
        if (null != e.getCause() && e.getCause().getCause() instanceof SizeLimitExceededException) {
            logger.warn(e.getMessage());
            SizeLimitExceededException ex = (SizeLimitExceededException) e.getCause().getCause();
            long psize = ex.getPermittedSize() / 1024 / 1024;
            return new ErrorMessage("文件大小：" + ex.getActualSize() + "字节，已超过最大上传大小：" + ex.getPermittedSize() + "字节(" + psize + "MB)");
        } else {
            if (e.getMessage().contains("is not a multipart request")) {
                logger.warn(e.getMessage());
                return new ErrorMessage("非multipart请求，Content-Type错误？正确为：multipart/form-data; boundary=...");
            }
            logger.warn(e.getMessage(), e);
            return new ErrorMessage(e.getLocalizedMessage());
        }
    }
    
}
