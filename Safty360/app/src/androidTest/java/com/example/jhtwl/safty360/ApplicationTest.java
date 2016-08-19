package com.example.jhtwl.safty360;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.example.jhtwl.safty360.Bean.BlackNumber;
import com.example.jhtwl.safty360.Dao.BlackNumberDao;

import java.util.List;
import java.util.Random;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAdd(){
        BlackNumberDao dao = new BlackNumberDao(mContext);
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            Long number = 13300000000l +i;
            dao.add(number +"",String.valueOf(random.nextInt(3) + 1));
        }
    }

    public void testDelete(){
        BlackNumberDao dao = new BlackNumberDao(mContext);
        boolean delete = dao.delete("13300000000");
        assertEquals(true,delete);
    }

    public void testFind(){
        BlackNumberDao dao = new BlackNumberDao(mContext);
        String number = dao.findNumber("13300000004");
        System.out.println(number);
    }

    public void testFindAll(){
        BlackNumberDao dao = new BlackNumberDao(mContext);
        List<BlackNumber> blackNumberInfos = dao.findAll();
        for (BlackNumber blackNumberInfo : blackNumberInfos) {
            System.out.println(blackNumberInfo.getMode() + "" + blackNumberInfo.getNumber());
        }
    }
}