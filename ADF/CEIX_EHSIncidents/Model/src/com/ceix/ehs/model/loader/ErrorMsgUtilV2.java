package com.ceix.ehs.model.loader;


import org.apache.commons.lang.exception.ExceptionUtils;


public class ErrorMsgUtilV2 {
    public ErrorMsgUtilV2() {
        super();
    }
    
    public static String stackTraceToString(Throwable e) {
        
           if(e==null)
             return "";

            Throwable rootcause = ExceptionUtils.getRootCause(e);
            if (rootcause == null) {
                rootcause = e;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(rootcause.getMessage());
            sb.append("\n");
            for (StackTraceElement element : rootcause.getStackTrace()) {
                sb.append(element.toString());
                sb.append("\n");
            }
            return sb.toString();


        }
      public static String truncateStackTrace(Throwable e, int charLimit) {
          if(e==null)
            return "";          
          String stackTrace = "";
          stackTrace = ErrorMsgUtilV2.stackTraceToString(e);
          if (e != null) {
              stackTrace = ErrorMsgUtilV2.stackTraceToString(e);
              if (stackTrace != null && stackTrace.length() > charLimit)
                  stackTrace = stackTrace.substring(0, charLimit);

          }
          return stackTrace;

      }
    
}
