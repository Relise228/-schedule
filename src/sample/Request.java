package sample;

public class Request {
    private int id;
     private String grupaa;
      private String prdmet_h;
       private int kstt;
        private String vchitel;

    public Request(String vchitel,String grupaa,String prdmet_h,int kstt) {
        this.vchitel = vchitel;
         this.grupaa = grupaa;
          this.prdmet_h  = prdmet_h;
           this.kstt  = kstt;
    }

    public  Request() {

    }

    public String getVchitel() {
        return vchitel;
    }

    public void setVchitel(String vchitel) {
        this.vchitel = vchitel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrupaa() {
        return grupaa;
    }

    public void setGrupaa(String grupaa) {
        this.grupaa = grupaa;
    }

    public String getPrdmet_h() {
        return prdmet_h;
    }

    public void setPrdmet_h(String prdmet_hh) {
        this.prdmet_h = prdmet_hh;
    }

    public int getKstt() {
        return kstt;
    }

    public void setKstt(int kstt) {
        this.kstt = kstt;
    }
}
