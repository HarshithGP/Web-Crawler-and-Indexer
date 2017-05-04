

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;

import com.google.common.io.Files;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.HashMap;

public class MyCrawler extends WebCrawler{
	
	public static HashMap<Integer, Integer> status = new HashMap<Integer, Integer>();
	public static Map<String,Integer> contentType_counter=new HashMap<String,Integer>();
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js"
	 + "|mp3|mp3|zip|gz))$");
	private static final Pattern imgPatterns = Pattern.compile(".*(\\.(gif|png|jpg|jpeg|tif|pdf|doc|html|htm|docx?))$");
	private CrawledData crawledData;
	private static File storageFolder;
			 
			 @Override
			 public void onStart() {
				 crawledData = new CrawledData();
			 }
	
			 
	/**
	   * The CrawlController instance that has created this crawler instance will
	   * call this function just before terminating this crawler thread. Classes
	   * that extend WebCrawler can override this function to pass their local
	   * data to their controller. The controller then puts these local data in a
	   * List that can then be used for processing the local data of crawlers (if needed).
	   *
	   * @return currently NULL
	   */
	 @Override
	  public Object getMyLocalData() {
	    return crawledData;
	  }
	  
	/**
	 * This method receives two parameters. The first parameter is the page
	 * in which we have discovered this new url and the second parameter is
	 * the new url. You should implement this function to specify whether
	 * the given url should be crawled or not (based on your crawling logic).
	 * In this example, we are instructing the crawler to ignore urls that
	 * have css, js, git, ... extensions and to only accept urls that start
	 * with "http://www.viterbi.usc.edu/". In this case, we didn't need the
	 * referringPage parameter to make the decision.
	 */
	 @Override
	 public boolean shouldVisit(Page referringPage, WebURL url) {
		 String href = url.getURL().toLowerCase();
		 crawledData.setTotalUrls(crawledData.getTotalUrls() + 1);
		 List<UrlData> urlDataList = crawledData.getUrlDataList();
		 UrlData urlData = new UrlData();
		 urlData.setUrlProcessed(url.getURL());
		 Location location;
		 if(url.getURL().contains("nbcnews.com")){
			 location = Location.OK;
			 crawledData.setUniqueURLInWebsite(Integer.toString(url.getURL().hashCode()));
		 }
		 else{ 
			 location = Location.N_OK;
			 crawledData.setUniqueURLOutsideWebsite(Integer.toString(url.getURL().hashCode()));
		 }
//		
		 urlData.setLocation(location);
		 urlDataList.add(urlData);
		 crawledData.setUrlDataList(urlDataList);
		 
		 return !FILTERS.matcher(href).matches()
		 && href.startsWith("http://www.nbcnews.com/");
	 }
	 /**
	  * This function is called when a page is fetched and ready
	  * to be processed by your program.
	  */
	  @Override
	  public void visit(Page page) {
		  String url = page.getWebURL().getURL();
		  setSizeCount(page.getContentData().length);
		  setContentType(page.getContentType().split(";")[0]);
		  System.out.println("URL: " + url);
		  if (page.getParseData() instanceof HtmlParseData) {
			  HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			  Set<WebURL> links = htmlParseData.getOutgoingUrls();
			  
			  String extension = ".html";
			  String hashedName = UUID.randomUUID() + extension;
		  
			  // store image
			  String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
				try {
					Files.write(page.getContentData(), new File(filename));
					logger.info("Stored: {}", url);
				} catch (IOException iox) {
					logger.error("Failed to write file: " + filename, iox);
				}
					List<VisitedData> visitedDataList = crawledData.getVisitedDataList();
					VisitedData visitedData = new VisitedData();
					visitedData.setContentLength(page.getContentData().length);
					visitedData.setOutlinkSize(links.size());
					visitedData.setType(page.getContentType());
					visitedData.setUrl(url);
					visitedDataList.add(visitedData);
					crawledData.setVisitedDataList(visitedDataList);
		  }
		  else if (imgPatterns.matcher(url).matches()) {
			  // get a unique name for storing this image
			  String extension = url.substring(url.lastIndexOf('.'));
			  String hashedName = UUID.randomUUID() + extension;
		
			  // store image
			  String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
			  try {
				  Files.write(page.getContentData(), new File(filename));
				  logger.info("Stored: {}", url);
			  } catch (IOException iox) {
				  logger.error("Failed to write file: " + filename, iox);
			  }
				    List<VisitedData> visitedDataList = crawledData.getVisitedDataList();
					VisitedData visitedData = new VisitedData();
					visitedData.setContentLength(page.getContentData().length);
					visitedData.setOutlinkSize(0);
					visitedData.setType(page.getContentType());
					visitedData.setUrl(url);
					visitedDataList.add(visitedData);
					crawledData.setVisitedDataList(visitedDataList);
			 }
	  }
	  private void setSizeCount(int length) {
		if(length < 1024){
			crawledData.setSize1(crawledData.getSize1() + 1);
		}else if(length < 10240){
			crawledData.setSize2(crawledData.getSize2() + 1);
		}else if(length < 102400){
			crawledData.setSize3(crawledData.getSize3() + 1);
		}else if(length < 1024000){
			crawledData.setSize4(crawledData.getSize4() + 1);
		}else{
			crawledData.setSize5(crawledData.getSize5() + 1);
		}	
	}


	@Override
	  protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
			crawledData.setFetchesAttempted(crawledData.getFetchesAttempted() + 1);
		  	updateStatusCodeCounts(statusCode);
		    List<FetchData> fetchDataList = crawledData.getFetchDataList();
		    FetchData fetchData = new FetchData();
		    fetchData.setUrl(webUrl.getURL());
		    fetchData.setHTTPStatusCode(statusCode);
		    fetchDataList.add(fetchData);
		    crawledData.setFetchDataList(fetchDataList);
	  }
	
	
	private void updateStatusCodeCounts(int statusCode) {
		if(statusCode/100==2){
			crawledData.setStatusOK(crawledData.getStatusOK() + 1);
			crawledData.setFetchesSucceeded(crawledData.getFetchesSucceeded() + 1);
		}else if(statusCode/100 == 3){
			//crawledData.setStatusMoved(crawledData.getStatusMoved() + 1);
			crawledData.setFetchesAborted(crawledData.getFetchesAborted() + 1);
		}else if(statusCode == HttpStatus.SC_NOT_FOUND){
			crawledData.setStatusNotFound(crawledData.getStatusNotFound() + 1);
			crawledData.setFetchesFailed(crawledData.getFetchesFailed() + 1);
		}else if(statusCode == HttpStatus.SC_UNAUTHORIZED){
			crawledData.setStatusUnauth(crawledData.getStatusUnauth() + 1);
			crawledData.setFetchesFailed(crawledData.getFetchesFailed() + 1);
		}else if(statusCode == HttpStatus.SC_FORBIDDEN){
			crawledData.setStatusForbidden(crawledData.getStatusForbidden() + 1);
			crawledData.setFetchesFailed(crawledData.getFetchesFailed() + 1);
		else{
			crawledData.setFetchesFailed(crawledData.getFetchesFailed()+1);
		}
		
		if(status.containsKey(statusCode)){
			status.put(statusCode,status.get(statusCode)+1);
		}else{
			status.put(statusCode,1);
		}
	}


	public static void configure(String crawlStorageFolder) {
		storageFolder = new File(crawlStorageFolder);
		if(!storageFolder.exists()){
			storageFolder.mkdirs();
		}
	}
	
	private void setContentType(String type) {
		if(contentType_counter.containsKey(type)){
	        contentType_counter.put(type, contentType_counter.get(type)+1);
	   }
	   else
	   {
	       contentType_counter.put(type, 1);
	   }
		if(type.compareTo("application/pdf") == 0){
			crawledData.setTypePdf(crawledData.getTypePdf() + 1);
		}else if(type.compareTo("image/gif") == 0){
			crawledData.setTypeGif(crawledData.getTypeGif() + 1);
		}else if(type.compareTo("application/doc") == 0){
			crawledData.setTypeDoc(crawledData.getTypeDoc() + 1);
		}else if(type.compareTo("text/html") == 0){
			crawledData.setTypeText(crawledData.getTypeText() + 1);
		}else if (type.compareTo("image/jpeg") == 0){
			crawledData.setTypeJpeg(crawledData.getTypeJpeg() + 1);
		}else if (type.compareTo("image/png") == 0){
			crawledData.setTypePng(crawledData.getTypePng() + 1);
		}
	}
}
