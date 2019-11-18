package sample;

public class rozkladclass {
    private String ponedilok;
     private String vivtorok;
      private String sereda;
       private String chetver;
        private String patnica;

    public rozkladclass(String ponedilok, String vivtorok, String sereda, String chetver, String patnica) {
        this.ponedilok = ponedilok;
         this.vivtorok = vivtorok;
          this.sereda = sereda;
           this.chetver = chetver;
            this.patnica = patnica;
    }

    public rozkladclass() {


    }

    public String getPonedilok() {
        return ponedilok;
    }

    public void setPonedilok(String ponedilok) {
        this.ponedilok = ponedilok;
    }

    public String getVivtorok() {
        return vivtorok;
    }

    public void setVivtorok(String vivtorok) {
        this.vivtorok = vivtorok;
    }

    public String getSereda() {
        return sereda;
    }

    public void setSereda(String sereda) {
        this.sereda = sereda;
    }

    public String getChetver() {
        return chetver;
    }

    public void setChetver(String chetver) {
        this.chetver = chetver;
    }

    public String getPatnica() {
        return patnica;
    }

    public void setPatnica(String patnica) {
        this.patnica = patnica;
    }


}




