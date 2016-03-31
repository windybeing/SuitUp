using System;
using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.Net.Security;
using System.Net.Sockets;
using System.Security.Authentication;
using System.Text;
using System.Security.Cryptography.X509Certificates;
using System.IO;

namespace Chpoi.SuitUp.SSL
{
    //SSL通信
    public static class SslTcpClient
    {
        public static TcpClient client;
        public static SslStream sslStream;
        
        private static Hashtable certificateErrors = new Hashtable();

        // The following method is invoked by the RemoteCertificateValidationDelegate.
        public static bool ValidateServerCertificate(
              object sender,
              X509Certificate certificate,
              X509Chain chain,
              SslPolicyErrors sslPolicyErrors)
        {
            if (sslPolicyErrors == SslPolicyErrors.None)
                return true;

            Console.WriteLine("Certificate error: {0}", sslPolicyErrors);

            // Do not allow this client to communicate with unauthenticated servers.
            return false;
        }

        public static bool RunClient(string machineName, string serverName)
        {
            // Create a TCP/IP client socket.
            // machineName is the host running the server application.
            client = new TcpClient(machineName, 6520);
            sslStream = new SslStream(
            client.GetStream(),
            false,
            new RemoteCertificateValidationCallback(ValidateServerCertificate),
            null
            );
            Console.WriteLine("Client connected.");
            // Create an SSL stream that will close the client's stream.
            // The server name must match the name on the server certificate.
            try
            {
                sslStream.AuthenticateAsClient(serverName);
            }
            catch (AuthenticationException e)
            {
                Console.WriteLine("Exception: {0}", e.Message);
                if (e.InnerException != null)
                {
                    Console.WriteLine("Inner exception: {0}", e.InnerException.Message);
                }
                Console.WriteLine("Authentication failed - closing the connection.");
                client.Close();
                return false;
            }
            // Encode a test message into a byte array.
            // Signal the end of the message using the "<EOF>".
            return true;
        }

        public static bool sendMessage(string message)
        {
            byte[] bmesssage = Encoding.UTF8.GetBytes(message);
            // Send hello message to the server. 
            sslStream.Write(bmesssage);
            sslStream.Flush();
            // Read message from the server.
            return true;
        }

      
        public static string ReadLength()
        {
            // Read the  message sent by the server.
            // The end of the message is signaled using the
            // "<EOF>" marker.
            byte[] buffer = new byte[1];
            StringBuilder messageData = new StringBuilder();
            int bytes = -1;
            do
            {
                bytes = sslStream.Read(buffer, 0, buffer.Length);
                // Use Decoder class to convert from bytes to UTF8
                // in case a character spans two buffers.
                Decoder decoder = Encoding.UTF8.GetDecoder();
                char[] chars = new char[decoder.GetCharCount(buffer, 0, bytes)];
                decoder.GetChars(buffer, 0, bytes, chars, 0);
                messageData.Append(chars);
                // Check for EOF.
                if (messageData.ToString().IndexOf("<EOF>\r\n") != -1)
                {
                    break;
                }
            } while (bytes != 0);

            return messageData.ToString();
        }
        public static string ReadMessage(int length)
        {
            // Read the  message sent by the server.
            // The end of the message is signaled using the
            // "<EOF>" marker.
            byte[] buffer = new byte[8*1024*1024];
            StringBuilder messageData = new StringBuilder();
            int bytes = -1;
            int blength = 0;
            do
            {
                bytes = sslStream.Read(buffer, 0, buffer.Length);
                // Use Decoder class to convert from bytes to UTF8
                // in case a character spans two buffers.
                Decoder decoder = Encoding.UTF8.GetDecoder();
                char[] chars = new char[decoder.GetCharCount(buffer, 0, bytes)];
                decoder.GetChars(buffer, 0, bytes, chars, 0);
                messageData.Append(chars);
                // Check for EOF.
                if (messageData.Length == length)
                {
                    break;
                }
            } while (bytes != 0);

            return messageData.ToString();
        }

        public static string ReadPicture(int length)
        {
            // Read the  message sent by the server.
            // The end of the message is signaled using the
            // "<EOF>" marker.
            byte[] buf = new byte[8 * 1024 * 1024];
            StringBuilder messageData = new StringBuilder(length);
            int bytes = -1;
            List<char> l = new List<char>();
            int rlength = 0;
            List<byte> b = new List<byte>();
            int blength = 0;
            do
            {
                byte[] buffer = new byte[1024 * 4];
                bytes = sslStream.Read(buffer, 0, buffer.Length);
                byte[] bufferfull = new byte[bytes];
                Array.Copy(buffer, 0, bufferfull, 0, bytes);
                blength += bytes;
                b.AddRange(bufferfull);
                Decoder decoder = Encoding.UTF8.GetDecoder();
                char[] chars = new char[decoder.GetCharCount(buffer, 0, bytes)];
                decoder.GetChars(buffer, 0, bytes, chars, 0);
                rlength += chars.Length;
                // Use Decoder class to convert from bytes to UTF8
                // in case a character spans two buffers.
                // Check for EOF.
                l.AddRange(chars);
                if (rlength == length)
                    break;

            } while (bytes != 0);




            byte[] allbyte = b.ToArray();
            Decoder decoder1 = Encoding.UTF8.GetDecoder();
            char[] allchars = new char[decoder1.GetCharCount(allbyte, 0, blength)];
            decoder1.GetChars(allbyte, 0, blength, allchars, 0);

            messageData.Append(allchars);

            return messageData.ToString();
        }
        

        private static void DisplayUsage()
        {
            Console.WriteLine("To start the client specify:");
            Console.WriteLine("clientSync machineName [serverName]");
            Environment.Exit(1);
        }
        public static bool IsConnect()
        {
            return client.Connected;    
        }
    }
}

