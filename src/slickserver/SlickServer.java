/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slickserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ric
 */
public class SlickServer {

    public static void main(String[] args) throws IOException {

        /*
        Server s1 = new Server("gw1.fra1.slickvpn.com");

        System.out.println(s1);
        
         Server s2 = new Server("gw1.fra2.slickvpn.com");

        System.out.println(s2);
         */
        File f1 = new File("ListeServeurs.txt");
        FileReader fr = new FileReader(f1);
        BufferedReader br = new BufferedReader(fr);

        File f2 = new File("AnalyseServers.txt");
        FileWriter fw = new FileWriter(f2);
        System.out.println("Is Execute allow : " + f2.canExecute());
        System.out.println("Is Write allow : " + f2.canWrite());
        System.out.println("Is Read allow : " + f2.canRead());
        BufferedWriter bw = new BufferedWriter(fw);

        int i = 0;
        String chaine = "";
        String line;
        while ((line = br.readLine()) != null) {
            i++;
            // System.out.println(line);
            Server si = new Server(line);
            if (si.isIsReachable()) {
                PingServer pingserver = new PingServer(si.getAdressIpv4());
                System.out.println(si.getName() + "," + pingserver);
                chaine = si.getName() + "," + pingserver + "\n";
                bw.write(chaine);

                // if I/O error occurs
            }
            bw.flush();
        }

        bw.close();


    } // main

} // SlickServer
