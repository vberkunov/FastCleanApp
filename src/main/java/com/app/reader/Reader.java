package com.app.reader;


import Reader.ReaderAPI;
import sdk.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Reader {
    private int m_Port;                 //Reader Port
    private int m_HostPort;             //Host Port
    private int ConnectMode;            //1---RS232，2---RS485
    private int ifConnectReader;        //1---true,0---false
    private int NewConnect;
    private String strHostIp;
    private String strReaderIp;
    private String strComm;
    private int res;
    private static int hScanner[] = new int[1];
    private static boolean running = true;
    private int iModAnt;	//选中的个数
    private int Read_times=0;
    private short iHostPort = 5000;
    private int iPlatform	=	0;
    private static int[] gAntenna = new int[4];
    private int Address                 =       0;
    private int nIDEvent = 1;
    private static int mem ;
    private static int ptr ;
    private static int len ;
    private static byte[] mask = new byte[512];
    private int timer_interval = 200;
    private static byte[][] EPCC1G2_IDBuffer = new byte[ReaderAPI.MAX_LABELS][ReaderAPI.ID_MAX_SIZE_96BIT];

    private int iTagNumber = 0;//auto下的tag的个数
    private static boolean bound = false;

    public Reader(int m_Port, int m_HostPort, int connectMode, int ifConnectReader, int newConnect, String hostIp, String readerIp, String strComm) {
        this.m_Port = m_Port;
        this.m_HostPort = m_HostPort;
        this.ConnectMode = connectMode; //0
        this.ifConnectReader = ifConnectReader;
        this.NewConnect = newConnect;
        this.strHostIp = hostIp;
        this.strReaderIp = readerIp;
        this.strComm = strComm;
    }

    public void connection() {

        int i = 0;
        int[] nBaudRate = new int[1];



        //iRet = ReaderAPI.Net_ConnectScanner(hScanner, "192.168.0.103", 1969, "192.168.0.71", 5555);
        res = ReaderAPI.Net_ConnectScanner(hScanner, strReaderIp, m_Port, strHostIp, m_HostPort);


        if (res == ReaderAPI._OK) {
            //连上了，选择哪个模式通迅变恢
            short[] HandVer = new short[1];
            short[] SoftVer = new short[1];

            for (i = 0; i < 5; i++) {

                res = ReaderAPI.Net_GetReaderVersion(hScanner[0], HandVer, SoftVer);


                if (res == ReaderAPI._OK) {
                    break;
                }
            }
            if (res != ReaderAPI._OK) {
                System.out.println("Connect Reader Fail!(Version)");

            }


            byte[] gBasicParam = new byte[32];
            for (i = 0; i < 5; i++) {

                res = ReaderAPI.Net_ReadBasicParam(hScanner[0], gBasicParam);


                if (res == ReaderAPI._OK) {
                    break;
                }


            }
            if (res != ReaderAPI._OK) {

                System.out.println("Connect Reader Fail!(Version)");

            }


            byte[] gAutoParam = new byte[32];
            for (i = 0; i < 5; i++) {
                res = ReaderAPI.Net_ReadAutoParam(hScanner[0], gAutoParam);


                if (res == ReaderAPI._OK) {
                    iHostPort = (short) ((((int) gAutoParam[21]) << 8) + (short) (255 - (byte) ~gAutoParam[22] + 0)); //auto host port
                    break;
                }


            }
            if (res != ReaderAPI._OK) {

                System.out.println("Connect Reader Fail!(Version)");
                return;
            }


            ifConnectReader = 1;
            NewConnect = 1;


        } else {
            System.out.println("Connect Reader Fail!(Version)");

        }


    }


    public String getEPC() {

        String strTemp = "";
        int m_LAddress = 0; //address
        int m_LLen = 0; //length
        int m_mem = 0;//bank


        mem = m_mem;
        ptr = m_LAddress;
        len = m_LLen;

        mask = Utility.convert2HexArray(strTemp);

        int i;


        int nIDEvent = 1;//是哪个事ID号, 列标签


        int k = 0;
        byte[] bTmpAnt = new byte[4];

        //m_antenna_sel=-1;
        int m_antenna_sel = 0;
        bTmpAnt[0] = 0;
        bTmpAnt[1] = 0;
        bTmpAnt[2] = 0;
        bTmpAnt[3] = 0;
        gAntenna[0] = 0;
        gAntenna[1] = 0;
        gAntenna[2] = 0;
        gAntenna[3] = 0;

        bTmpAnt[0] = 1;


        for (i = 0; i < 4; i++) {
            if (bTmpAnt[i] != 0) {
                gAntenna[k] = bTmpAnt[i];
                k++;
            }
        }


        res = ReaderAPI.Net_SetAntenna(hScanner[0], m_antenna_sel);


        if (res != ReaderAPI._OK) {
            System.out.println("Antenna Failed");
        }

        String epc = run();

        return epc;
    }



        public static String run() {
            boolean isFound = false;
            String epc = new String();

           while(!isFound){

                    int res = -1;
                    int i, ID_len = 0, ID_len_temp = 0;
                    String str = "";
                    byte[] temp = new byte[64 * 2];
                    byte[] IDBuffer = new byte[30 * 256];
                    int[] nCounter = new int[2];



                    res = ReaderAPI.Net_EPC1G2_ReadLabelID(hScanner[0], mem, ptr, len, mask, IDBuffer, nCounter);



                    if (res == ReaderAPI._OK) {
                                //这句是为了测试用
                                if (nCounter[0] > 8) {
                                    i = nCounter[0];
                                }

                                for (i = 0; i < nCounter[0]; i++) {
                                    if (IDBuffer[ID_len] > 32) {
                                        nCounter[0] = 0;
                                        break;
                                    }
                                    ID_len_temp = IDBuffer[ID_len] * 2 + 1;//1word=16bit
                                    System.arraycopy(IDBuffer, ID_len, EPCC1G2_IDBuffer[i], 0, ID_len_temp);
                                    ID_len += ID_len_temp;
                                }


                                for (i = 0; i < 1; i++) {

                                    ID_len = EPCC1G2_IDBuffer[i][0] * 2;
                                    System.arraycopy(EPCC1G2_IDBuffer[i], 1, temp, 0, ID_len);

                                    //将字节转16进制字符串 0x31 0x32 ===> "3132"
                                    epc = Utility.bytes2HexString(temp, ID_len);
                                    System.out.println(epc);
                                    if(str != null){
                                        isFound = true;
                                    }



                                }

                            }


            }
           return epc;
        }






}