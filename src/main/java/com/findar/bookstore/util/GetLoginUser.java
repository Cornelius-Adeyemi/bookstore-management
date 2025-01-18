package com.findar.bookstore.util;

import com.findar.bookstore.config.security.SecurityDetailsHolder;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetLoginUser {


   public static SecurityDetailsHolder getLoginUser(){

      Object contextInfo = SecurityContextHolder.getContext().getAuthentication();
      if(contextInfo == null ){
         return null;

      }
       return (SecurityDetailsHolder) contextInfo;
   }
}
