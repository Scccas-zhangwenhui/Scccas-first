package util;

import java.security.MessageDigest;

public class MD5Util {
    /**
     * ʹ��MD5�����㷨���ַ���ת����32λ��MD5ɢ��ֵ
     * @param inStr �����ܵ��ַ���
     * @return ���ܺ��32λMD5ɢ���ַ������������ʧ�ܷ��ؿ��ַ���
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        // ���ַ���ת��Ϊ�ַ����飬��ת��Ϊ�ֽ�����
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        // ִ��MD5���ܲ���
        byte[] md5Bytes = md5.digest(byteArray);
        // �����ܺ���ֽ�����ת��Ϊʮ�������ַ���
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        // ����32λ��MD5ɢ���ַ���
        return hexValue.toString();
    }

    /**
     * �Զ���MD5���ܹ��򣬽��ַ�������һ���������MD5����
     * @param inStr �����ܵ��ַ���
     * @return ���ܺ��32λMD5ɢ���ַ���
     */
    public static String MD5(String inStr) {
        String xy = "abc";
        String finalStr = "";
        if (inStr != null) {
            // ȡ�ַ����ĵ�һ���ַ���"abc"ƴ�ӣ�Ȼ�����ַ��������ಿ��ƴ��
            String fStr = inStr.substring(0, 1);
            String lStr = inStr.substring(1, inStr.length());
            // ����string2MD5��������MD5����
            finalStr = string2MD5(fStr + xy + lStr);
        } else {
            // ���������ַ���Ϊnull����ֱ�Ӽ���"abc"
            finalStr = string2MD5(xy);
        }
        // ���ؼ��ܺ���ַ���
        return finalStr;
    }
}
