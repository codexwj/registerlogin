package com.codejames.registerlogin.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.codejames.registerlogin.dto.BaseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class SecurityUtil {

    private final static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static String sortForm(BaseDto info) throws Exception {
        String json = JSONObject.toJSONString(info, SerializerFeature.MapSortField);
        logger.info("json :"+json);
        return shaEncode(json);
    }

    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static void main(String args[]) throws Exception {
        String str = new String("{\"appId\":\"1117\",\"regionCode\":\"12198\",\"timestamp\":\"1625809157\",\"userEmail\":\"test@qq.com\"}");
        System.out.println("原始：" + str);
        System.out.println("SHA后：" + shaEncode(str));

    }
    }
