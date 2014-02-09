package net.juniper.jmp.persist.jdbc;

import java.io.InputStream;
import java.io.Reader;
/**
 * 
 * @author juntaod
 *
 */
public final class ParamTypeFactory {
   public static SQLParamType getNullType(int type) {
	   return new NullParamType(type);
   }
   
   public static SQLParamType getBlobType(Object obj) {
	   return new BlobParamType(obj);
   }
   
   public static SQLParamType getBlobType(byte[] bytes) {
	   return new BlobParamType(bytes);
   }

   public static SQLParamType getBlobType(InputStream input, int length) {
	   return new BlobParamType(input, length);
   }

   public static SQLParamType getClobType(Reader reader) {
       return new ClobParamType(reader);
   }

   public static SQLParamType getClobType(String str) {
       return new ClobParamType(str);
   }
}
