
import com.vmm.JHTTPServer;
import java.util.Properties;
import java.sql.*;

public class myServer extends JHTTPServer {

    myServer(int port) throws Exception {
        super(port);
    }

    @Override
    public Response serve(String uri, String method, Properties headers, Properties parms, Properties files) {
        Response obj = new Response(HTTP_OK, "text/plain", "THIS IS PROJECT CLASS");

        System.out.println("uri-------------------------->" + uri);
        if (uri.contains("sendname")) {
            String name = parms.getProperty("username");
            obj = new Response(HTTP_OK, "text/plain", "Client send name:" + name);
        } else if (uri.contains("recievephoto")) {
            String name = parms.getProperty("name");
            String filename = saveFileOnServerWithRandomName(files, parms, "photo", "src/uploads");
            String filepath = "src/uploads/" + filename;
            System.out.println("File name is :" + filepath);
            obj = new Response(HTTP_OK, "text/plain", "Photo saved Successfully");
        } else if (uri.contains("login")) {
            String username = parms.getProperty("username");
            String password = parms.getProperty("password");

            try {
                ResultSet rs = DBLOADER.executeStatement("select * from users where username='" + username + "' and password='" + password + "'");
                if (rs.next()) {
                    obj = new Response(HTTP_OK, "text/plain", "LOGIN SUCCESSFULL");

                } else {
                    obj = new Response(HTTP_OK, "text/plain", "LOGIN FAILS");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (uri.contains("signup")) {
            String username = parms.getProperty("username");
            String email = parms.getProperty("email");
            String phone = parms.getProperty("phone");
            try {
                ResultSet rs = DBLOADER.executeStatement("select * from users where email='" + email + "'");
                if (rs.next()) {
                    obj = new Response(HTTP_OK, "text/plain", "E-Mail alfready Exists");
                } else {
                    String filename = saveFileOnServerWithRandomName(files, parms, "photo", "src/uploads");
                    rs.moveToInsertRow();
                    rs.updateString("username", username);
                    rs.updateString("email", email);
                    rs.updateString("phone", phone);
                    rs.updateString("photo", "src/uploads/" + filename);
                    rs.insertRow();
                    obj = new Response(HTTP_OK, "text/plain", "Sign up successfull");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return obj;
    }

}
