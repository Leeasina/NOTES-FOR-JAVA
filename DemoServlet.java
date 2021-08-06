package demotest;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.*;


public class DemoServlet extends javax.servlet.http.HttpServlet{
    protected void doPost(javax.servlet.http.HttpServletRequest request,javax.servlet.http.HttpServletResponse response) throws IOException {
        ServletInputStream sis = request.getInputStream();
        //处理Post请求时进行反序列化操作
        ObjectInputStream ois = new ObjectInputStream(sis);
        try{
            ois.readObject();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        ois.close();
        }
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("This is a demo! ");
    }
}
