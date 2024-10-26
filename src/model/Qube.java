package model;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Qube {

    private static Logger logger = Logger.getLogger("Pharmacy");

//    public static DecimalFormat df = new DecimalFormat("0.00");
    public static DecimalFormat df;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(' ');  // Use space as the grouping separator

        df = new DecimalFormat("#,##0.00", symbols);  // Pattern for grouping and two decimal places
    }

    public static void searchBing(String query) {
        if (isInternetAvailable()) {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
                    String url = "https://www.bing.com/search?q=" + encodedQuery;
                    desktop.browse(new URI(url));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Desktop is not supported.", "Cloudn't Connect", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Internet Conection is not Avalable.", "Cloudn't Connect", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isInternetAvailable() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setConnectTimeout(5000); // 5 seconds timeout
            urlConnect.connect();
            return urlConnect.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public Logger setLogger(String fileName) {

        try {
            FileHandler fileHandler = new FileHandler(fileName, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }

    public static Result readBarCode(String barCode) {
        try {
            InputStream barInput = new FileInputStream(barCode);
            BufferedImage bI = ImageIO.read(barInput);
            LuminanceSource sourece = new BufferedImageLuminanceSource(bI);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(sourece));
            Reader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }
    

}
