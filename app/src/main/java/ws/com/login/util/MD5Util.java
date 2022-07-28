package ws.com.login.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static final String SALT = "ws";
    public static String md5s(String plainText) {
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
            System.out.println("result: " + buf.toString());// 32位的加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }
}
