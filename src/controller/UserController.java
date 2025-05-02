package controller;

import model.MapModel;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserController {

    private MapModel mapModel;




    public static boolean isValidUser(String username,String password){
        //todo 用于验证是否与存储的data相匹配。无当前用户/密码不正确/放行
        return true;
    }



}
