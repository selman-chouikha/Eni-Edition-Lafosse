package betaboutique.outils;


import com.missiondata.fileupload.OutputStreamListener;

/**
 * Classe FileUploadListener
 * @author Jérôme Lafosse
 * @version 0.01
 */
public class FileUploadListener implements OutputStreamListener
{
  // Créer un objet 
  private FileUploadStats fileUploadStats=new FileUploadStats();

  // Constructeur
  public FileUploadListener(long totalSize)
  {
    fileUploadStats.setTotalSize(totalSize);
  }

  public void start()
  {
    fileUploadStats.setCurrentStatus("start");
  }

  public void bytesRead(int byteCount)
  {
    fileUploadStats.incrementBytesRead(byteCount);
    fileUploadStats.setCurrentStatus("reading");
  }

  public void error(String s)
  {
    fileUploadStats.setCurrentStatus("error");
  }

  public void done()
  {
    fileUploadStats.setBytesRead(fileUploadStats.getTotalSize());
    fileUploadStats.setCurrentStatus("done");
  }

  public FileUploadStats getFileUploadStats()
  {
    return fileUploadStats;
  }

  // Classe statique pour les infos
  public static class FileUploadStats
  {
    private long totalSize=0;
    private long bytesRead=0;
    private long startTime=System.currentTimeMillis();
    private String currentStatus="none";

    public long getTotalSize()
    {
      return totalSize;
    }

    public void setTotalSize(long totalSize)
    {
      this.totalSize=totalSize;
    }

    public long getBytesRead()
    {
      return bytesRead;
    }

    public long getElapsedTimeInSeconds()
    {
      return (System.currentTimeMillis()-startTime)/1000;
    }

    public String getCurrentStatus()
    {
      return currentStatus;
    }

    public void setCurrentStatus(String currentStatus)
    {
      this.currentStatus=currentStatus;
    }

    public void setBytesRead(long bytesRead)
    {
      this.bytesRead=bytesRead;
    }

    public void incrementBytesRead(int byteCount)
    {
      this.bytesRead+=byteCount;
    }
  }
  
}
