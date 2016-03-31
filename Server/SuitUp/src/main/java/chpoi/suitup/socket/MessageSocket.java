package chpoi.suitup.socket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

/**
 * MessengSocket is responsible for managing the socket of Server
 * 
 * @author Dong Zhiyuan
 */
public class MessageSocket {

    private static String SERVER_KEY_STORE = "server_ks";  
    private static String SERVER_KEY_STORE_PASSWORD = "123123";
    
    /**
     * This method generates a static (@link SSLServerSocket} with given public key.
     * 
     * @author Dong Zhiyuan
     */
    public static ServerSocket generalServerSocket() {
        try {
			System.setProperty("javax.net.ssl.trustStore", SERVER_KEY_STORE);  
			SSLContext context = SSLContext.getInstance("TLS");  

			KeyStore keyStone = KeyStore.getInstance("jceks");  
			keyStone.load(new FileInputStream(SERVER_KEY_STORE), null);  
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");  
			keyManagerFactory.init(keyStone, SERVER_KEY_STORE_PASSWORD.toCharArray());  
			context.init(keyManagerFactory.getKeyManagers(), null, null);  
  
			ServerSocketFactory serverSocketFactory = context.getServerSocketFactory();  
			ServerSocket serverSocket = serverSocketFactory.createServerSocket(6520);  
			((SSLServerSocket) serverSocket).setNeedClientAuth(false);  
			return serverSocket;
		} catch (Exception e) {	}
        return null;
    }

}
