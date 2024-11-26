package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    /**
     * 生成新的文件名，避免文件上传时文件名冲突
     * @param oldFileName 原始文件名
     * @return 新的文件名，格式为当前时间戳_原始扩展名
     */
    public static String getNewFileName(String oldFileName) {
        // 获取文件扩展名
        int lastIndex = oldFileName.lastIndexOf(".");
        String fileType = oldFileName.substring(lastIndex);
        // 获取当前时间
        Date now = new Date();
        // 定义时间格式化对象，格式为年月日时分秒毫秒
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmssSSS");
        // 格式化当前时间
        String time = sdf.format(now);
        // 生成新的文件名
        String newFileName = time + fileType;
        // 返回新的文件名
        return newFileName;
    }
}