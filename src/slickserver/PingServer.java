/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slickserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 *
 * @author ric
 */
public class PingServer {

private String ipv4;  
private double loss;
private double min;
private double avg;
private double max;
private int ttl;    
private int transmitted;
private int received;
private double stddev;  
private String message;
private String cmdnb="2 ";



    public String doPing() {
        String reponse = "";
        try {
            String s = "";
            ProcessBuilder pb = new ProcessBuilder("ping", "-c", cmdnb, this.ipv4);
            Process process = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((s = stdInput.readLine()) != null) {
                reponse += s + "\n";
            }
        } catch (IOException ex) {
            reponse = null;
        }
        return reponse;
    }
    
    
        public void doAnalyse() {
            
          String reponse =doPing();  

        Pattern PAT_REST = Pattern.compile(
                "(?<TRANSMITTED>\\d+)\\s+packets transmitted.*"
                + "(?<RECEIVED>\\d+)\\s+(packets )?received.*"
                + "(?<LOSS>\\d+)% packet loss.*" + "min/avg/max/(stddev|mdev)\\s+=\\s+"
                + "(?<MIN>\\d+\\.\\d+)/"
                + "(?<AVG>\\d+\\.\\d+)/"
                + "(?<MAX>\\d+\\.\\d+)/"
                + "(?<STDDEV>\\d+\\.\\d+)",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        Matcher m2 = PAT_REST.matcher(reponse);
        m2.find();

        this.transmitted = Integer.parseInt(m2.group("TRANSMITTED"));
        this.received = Integer.parseInt(m2.group("RECEIVED"));
        this.loss = Double.parseDouble(m2.group("LOSS"));
        this.min = Double.parseDouble(m2.group("MIN"));
        this.avg = Double.parseDouble(m2.group("AVG"));
        this.max = Double.parseDouble(m2.group("MAX"));
        this.stddev = Double.parseDouble(m2.group("STDDEV"));

        Pattern pattern = Pattern.compile(Pattern.quote("ttl=") + "(.*?)" + Pattern.quote("time="));

        Matcher matcher = pattern.matcher(reponse);
        matcher.find();
        String res = matcher.group(1);
        res = res.replaceAll("\\s+$", "");
        this.ttl = Integer.parseInt(res);

    }

//    @Override
//    public String toString() {
//        return "PingServer{" + "ttl=" + ttl + ", transmitted=" + transmitted + ", received=" + received + ", loss=" + loss + ", min=" + min + ", avg=" + avg + ", max=" + max + ", stddev=" + stddev + ", ipv4=" + ipv4 + ", message=" + message + ", cmdnb=" + cmdnb + '}';
//    }

    @Override
    public String toString() {
        return ipv4 +","+loss +","+min+","+avg+","+max+","+ttl;
            
    }


        
        
        
    public PingServer(String ipv4) {
        this.ipv4 = ipv4;
        doAnalyse();
    }

    public int getTransmitted() {
        return transmitted;
    }

    public String getCmdnb() {
        return cmdnb;
    }

    public void setCmdnb(String cmdnb) {
        this.cmdnb = cmdnb;
    }

    public int getReceived() {
        return received;
    }

    public double getLoss() {
        return loss;
    }

    public double getMin() {
        return min;
    }

    public double getAvg() {
        return avg;
    }

    public double getMax() {
        return max;
    }

    public double getStddev() {
        return stddev;
    }

    public String getIpv4() {
        return ipv4;
    }
    
    
    

      
    
}
