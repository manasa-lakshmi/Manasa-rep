package com.ceix.geolabs.loader;


import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

import java.util.logging.Level;


/**
 * Bean to represent a Geo lab file and its fields. Used for Geo lab report CSV reader
 * job.
 * @author Romesh Soni
 *
 */
public class GeoLabReportBean implements Serializable {


    private static final long serialVersionUID = 1L;
    
    @CsvBindByName
    private String TRAIN;
    @CsvBindByName
    private String YYMM;
    @CsvBindByName
    private String CARS;
    @CsvBindByName
    private String DATESAMP;
    @CsvBindByName
    private String RPTSAMPNO;
    @CsvBindByName
    private String SAMPMETHOD;
    @CsvBindByName
    private String FSI;
    @CsvBindByName
    private String TM;
    @CsvBindByName
    private String ASH_ASR;
    @CsvBindByName
    private String ASH_DRY;
    @CsvBindByName
    private String VOL_ASR;
    @CsvBindByName
    private String VOL_DRY;
    @CsvBindByName
    private String VOL_DAF;
    @CsvBindByName
    private String SUL_ASR;
    @CsvBindByName
    private String SUL_DRY;
    @CsvBindByName
    private String SUL_DAF;
    @CsvBindByName
    private String FC_ASR;
    @CsvBindByName
    private String FC_DRY;
    @CsvBindByName
    private String FC_DAF;
    @CsvBindByName
    private String BTU_ASR;
    @CsvBindByName
    private String BTU_DRY;
    @CsvBindByName
    private String BTU_DAF;
    @CsvBindByName
    private String AFTI_RED;
    @CsvBindByName
    private String AFTS_RED;
    @CsvBindByName
    private String AFTH_RED;
    @CsvBindByName
    private String AFTF_RED;
    @CsvBindByName
    private String CHLORINE;
    @CsvBindByName
    private String MERCURY;
    @CsvBindByName(column = "LBS S")
    private String LBSS;
    @CsvBindByName(column = "LBS SO2")
    private String LBSSO2;
    @CsvBindByName(column = " SAMPLEWT")
    private String  SAMPLEWT;
    @CsvBindByName(column = "Tonnage")
    private String Tonnage;
    @CsvBindByName(column = "% RECOVERY")
    private String PER_RECOVERY;
    @CsvBindByName(column = "ROM MOISTURE")
    private String ROM_MOISTURE;
    @CsvBindByName
    private String RAWASH;
    @CsvBindByName(column= "Raw Tons")
    private String RAW_TONS;
    @CsvBindByName(column= "Clean Tons")
    private String CLEAN_TONS;
    @CsvBindByName(column= "Oxidation")
    private String OXIDATION;

    public GeoLabReportBean() {
        super();
    }


    public void setTRAIN(String TRAIN) {
        this.TRAIN = TRAIN;
    }

    public String getTRAIN() {
        return TRAIN;
    }

    public void setYYMM(String YYMM) {
        this.YYMM = YYMM;
    }

    public String getYYMM() {
        return YYMM;
    }

    public void setCARS(String CARS) {
        this.CARS = CARS;
    }

    public String getCARS() {
        return CARS;
    }

    public void setDATESAMP(String DATESAMP) {
        this.DATESAMP = DATESAMP;
    }

    public String getDATESAMP() {
        return DATESAMP;
    }

    public void setRPTSAMPNO(String RPTSAMPNO) {
        this.RPTSAMPNO = RPTSAMPNO;
    }

    public String getRPTSAMPNO() {
        return RPTSAMPNO;
    }

    public void setSAMPMETHOD(String SAMPMETHOD) {
        this.SAMPMETHOD = SAMPMETHOD;
    }

    public String getSAMPMETHOD() {
        return SAMPMETHOD;
    }

    public void setFSI(String FSI) {
        this.FSI = FSI;
    }

    public String getFSI() {
        return FSI;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    public String getTM() {
        return TM;
    }

    public void setASH_ASR(String ASH_ASR) {
        this.ASH_ASR = ASH_ASR;
    }

    public String getASH_ASR() {
        return ASH_ASR;
    }

    public void setASH_DRY(String ASH_DRY) {
        this.ASH_DRY = ASH_DRY;
    }

    public String getASH_DRY() {
        return ASH_DRY;
    }

    public void setVOL_ASR(String VOL_ASR) {
        this.VOL_ASR = VOL_ASR;
    }

    public String getVOL_ASR() {
        return VOL_ASR;
    }

    public void setVOL_DRY(String VOL_DRY) {
        this.VOL_DRY = VOL_DRY;
    }

    public String getVOL_DRY() {
        return VOL_DRY;
    }

    public void setVOL_DAF(String VOL_DAF) {
        this.VOL_DAF = VOL_DAF;
    }

    public String getVOL_DAF() {
        return VOL_DAF;
    }

    public void setSUL_ASR(String SUL_ASR) {
        this.SUL_ASR = SUL_ASR;
    }

    public String getSUL_ASR() {
        return SUL_ASR;
    }

    public void setSUL_DRY(String SUL_DRY) {
        this.SUL_DRY = SUL_DRY;
    }

    public String getSUL_DRY() {
        return SUL_DRY;
    }

    public void setSUL_DAF(String SUL_DAF) {
        this.SUL_DAF = SUL_DAF;
    }

    public String getSUL_DAF() {
        return SUL_DAF;
    }

    public void setFC_ASR(String FC_ASR) {
        this.FC_ASR = FC_ASR;
    }

    public String getFC_ASR() {
        return FC_ASR;
    }

    public void setFC_DRY(String FC_DRY) {
        this.FC_DRY = FC_DRY;
    }

    public String getFC_DRY() {
        return FC_DRY;
    }

    public void setFC_DAF(String FC_DAF) {
        this.FC_DAF = FC_DAF;
    }

    public String getFC_DAF() {
        return FC_DAF;
    }

    public void setBTU_ASR(String BTU_ASR) {
        this.BTU_ASR = BTU_ASR;
    }

    public String getBTU_ASR() {
        return BTU_ASR;
    }

    public void setBTU_DRY(String BTU_DRY) {
        this.BTU_DRY = BTU_DRY;
    }

    public String getBTU_DRY() {
        return BTU_DRY;
    }

    public void setBTU_DAF(String BTU_DAF) {
        this.BTU_DAF = BTU_DAF;
    }

    public String getBTU_DAF() {
        return BTU_DAF;
    }

    public void setAFTI_RED(String AFTI_RED) {
        this.AFTI_RED = AFTI_RED;
    }

    public String getAFTI_RED() {
        return AFTI_RED;
    }

    public void setAFTS_RED(String AFTS_RED) {
        this.AFTS_RED = AFTS_RED;
    }

    public String getAFTS_RED() {
        return AFTS_RED;
    }

    public void setAFTH_RED(String AFTH_RED) {
        this.AFTH_RED = AFTH_RED;
    }

    public String getAFTH_RED() {
        return AFTH_RED;
    }

    public void setAFTF_RED(String AFTF_RED) {
        this.AFTF_RED = AFTF_RED;
    }

    public String getAFTF_RED() {
        return AFTF_RED;
    }

    public void setCHLORINE(String CHLORINE) {
        this.CHLORINE = CHLORINE;
    }

    public String getCHLORINE() {
        return CHLORINE;
    }

    public void setMERCURY(String MERCURY) {
        this.MERCURY = MERCURY;
    }

    public String getMERCURY() {
        return MERCURY;
    }

    public void setLBSS(String LBSS) {
        this.LBSS = LBSS;
    }

    public String getLBSS() {
        return LBSS;
    }

    public void setLBSSO2(String LBSSO2) {
        this.LBSSO2 = LBSSO2;
    }

    public String getLBSSO2() {
        return LBSSO2;
    }

    public void setSAMPLEWT(String SAMPLEWT) {
        this.SAMPLEWT = SAMPLEWT;
    }

    public String getSAMPLEWT() {
        return SAMPLEWT;
    }

    public void setPER_RECOVERY(String PER_RECOVERY) {
        this.PER_RECOVERY = PER_RECOVERY;
    }

    public String getPER_RECOVERY() {
        return PER_RECOVERY;
    }

    public void setROM_MOISTURE(String ROM_MOISTURE) {
        this.ROM_MOISTURE = ROM_MOISTURE;
    }

    public String getROM_MOISTURE() {
        return ROM_MOISTURE;
    }

    public void setRAWASH(String RAWASH) {
        this.RAWASH = RAWASH;
    }

    public String getRAWASH() {
        return RAWASH;
    }

    public void setTonnage(String Tonnage) {
        this.Tonnage = Tonnage;
    }

    public String getTonnage() {
        return Tonnage;
    }

    public void setRAW_TONS(String RAW_TONS) {
        this.RAW_TONS = RAW_TONS;
    }

    public String getRAW_TONS() {
        return RAW_TONS;
    }

    public void setCLEAN_TONS(String CLEAN_TONS) {
        this.CLEAN_TONS = CLEAN_TONS;
    }

    public String getCLEAN_TONS() {
        return CLEAN_TONS;
    }

    public void setOXIDATION(String OXIDATION) {
        this.OXIDATION = OXIDATION;
    }

    public String getOXIDATION() {
        return OXIDATION;
    }
}
