package util;

import java.security.MessageDigest;

public class MD5Util {
    /**
     * 使用MD5加密算法将字符串转换成32位的MD5散列值
     * @param inStr 待加密的字符串
     * @return 加密后的32位MD5散列字符串，如果加密失败返回空字符串
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        // 将字符串转换为字符数组，并转换为字节数组
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        // 执行MD5加密操作
        byte[] md5Bytes = md5.digest(byteArray);
        // 将加密后的字节数组转换为十六进制字符串
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        // 返回32位的MD5散列字符串
        return hexValue.toString();
    }

    /**
     * 自定义MD5加密规则，将字符串按照一定规则进行MD5加密
     * @param inStr 待加密的字符串
     * @return 加密后的32位MD5散列字符串
     */
    public static String MD5(String inStr) {
        String xy = "abc";
        String finalStr = "";
        if (inStr != null) {
            // 取字符串的第一个字符和"abc"拼接，然后与字符串的其余部分拼接
            String fStr = inStr.substring(0, 1);
            String lStr = inStr.substring(1, inStr.length());
            // 调用string2MD5方法进行MD5加密
            finalStr = string2MD5(fStr + xy + lStr);
        } else {
            // 如果传入的字符串为null，则直接加密"abc"
            finalStr = string2MD5(xy);
        }
        // 返回加密后的字符串
        return finalStr;
    }
}
