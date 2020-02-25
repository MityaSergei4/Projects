import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetIp {
    public static void main(String[] args) throws IOException {
        String str = getIp();
        send(str);
    }

    private static String getIp() throws IOException {
        URL url = new URL("https://myip.by");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String str = null;
        Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");
        Matcher matcher;

        while (reader.ready()) {
            str = reader.readLine();
            matcher = pattern.matcher(str);
            if (matcher.find()) {
                str = str.substring(matcher.start(), matcher.end());
                break;
            }
        }
        return str;
    }
    private static void send(String str) {

        final String KEY_API = "1fgiibsCCyAAAAAAAAABEXv_LsAB8CYMTsycJJyFNiDkYT8JxLTPfMQYlZ73Brj9";

        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/net_ip").build();
        DbxClientV2 client = new DbxClientV2(config, KEY_API);

        try {
            InputStream input = new ByteArrayInputStream(str.getBytes());
            client.files().uploadBuilder("/" + str).uploadAndFinish(input);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
