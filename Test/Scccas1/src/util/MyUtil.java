package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    /**
     * �����µ��ļ����������ļ��ϴ�ʱ�ļ�����ͻ
     * @param oldFileName ԭʼ�ļ���
     * @return �µ��ļ�������ʽΪ��ǰʱ���_ԭʼ��չ��
     */
    public static String getNewFileName(String oldFileName) {
        // ��ȡ�ļ���չ��
        int lastIndex = oldFileName.lastIndexOf(".");
        String fileType = oldFileName.substring(lastIndex);
        // ��ȡ��ǰʱ��
        Date now = new Date();
        // ����ʱ���ʽ�����󣬸�ʽΪ������ʱ�������
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmssSSS");
        // ��ʽ����ǰʱ��
        String time = sdf.format(now);
        // �����µ��ļ���
        String newFileName = time + fileType;
        // �����µ��ļ���
        return newFileName;
    }
}