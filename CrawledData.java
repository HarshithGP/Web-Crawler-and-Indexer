

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrawledData {
	List<FetchData> fetchDataList;
	List<VisitedData> visitedDataList;
	List<UrlData> urlDataList;
	int fetchesAttempted;
	int fetchesSucceeded;
	int fetchesAborted;
	int fetchesFailed;
	int totalUrls;
	Set<String> uniqueURLInWebsite;
	Set<String> uniqueURLOutsideWebsite;
	Set<String> uniqueURLOutsideUSC;
	int statusOK;
	int statusMoved;
	int statusUnauth;
	int statusForbidden;
	int statusNotFound;
	int size1;
	int size2;
	int size3;
	int size4;
	int size5;
	int typeText;
	int typeGif;
	int typePng;
	int typePdf;
	int typeJpeg;
	int typeDoc;
	
	public static HashMap<Integer, Integer> statusCounter= new HashMap<Integer, Integer>();
	
	public CrawledData(){
		fetchDataList = new ArrayList<FetchData>();
		visitedDataList = new ArrayList<VisitedData>();
		urlDataList = new ArrayList<UrlData>();
		fetchesAttempted = 0;
		fetchesSucceeded = 0;
		fetchesAborted = 0;
		fetchesFailed = 0;
		totalUrls = 0;
		uniqueURLInWebsite = new HashSet<String>();
		uniqueURLOutsideWebsite = new HashSet<String>();
		uniqueURLOutsideUSC = new HashSet<String>();
		statusOK = 0;
		statusMoved = 0;
		statusUnauth = 0;
		statusForbidden = 0;
		statusNotFound = 0;
		size1 = 0;
		size2 = 0;
		size3 = 0;
		size4 = 0;
		size5 = 0;
		typeText = 0;
		typeGif = 0;
		typePng = 0;
		typePdf = 0;
		typeJpeg = 0;
		typeDoc = 0;
	}
	
	public List<FetchData> getFetchDataList() {
		return fetchDataList;
	}
	public void setFetchDataList(List<FetchData> fetchDataList) {
		this.fetchDataList = fetchDataList;
	}
	public List<VisitedData> getVisitedDataList() {
		return visitedDataList;
	}
	public void setVisitedDataList(List<VisitedData> visitedDataList) {
		this.visitedDataList = visitedDataList;
	}
	public List<UrlData> getUrlDataList() {
		return urlDataList;
	}
	public void setUrlDataList(List<UrlData> urlDataList) {
		this.urlDataList = urlDataList;
	}
	public int getFetchesAttempted() {
		return fetchesAttempted;
	}

	public void setFetchesAttempted(int fetchesAttempted) {
		this.fetchesAttempted = fetchesAttempted;
	}

	public int getFetchesSucceeded() {
		return fetchesSucceeded;
	}

	public void setFetchesSucceeded(int fetchesSucceeded) {
		this.fetchesSucceeded = fetchesSucceeded;
	}

	public int getFetchesAborted() {
		return fetchesAborted;
	}

	public void setFetchesAborted(int fetchesAborted) {
		this.fetchesAborted = fetchesAborted;
	}

	public int getFetchesFailed() {
		return fetchesFailed;
	}

	public void setFetchesFailed(int fetchesFailed) {
		this.fetchesFailed = fetchesFailed;
	}
	
	public int getTotalUrls() {
		return totalUrls;
	}

	public void setTotalUrls(int totalUrls) {
		this.totalUrls = totalUrls;
	}

	public Set<String>  getUniqueURLInWebsite() {
		return uniqueURLInWebsite;
	}

	public void setUniqueURLInWebsite(String uniqueURLInWebsite) {
		this.uniqueURLInWebsite.add(uniqueURLInWebsite);
	}

	public Set<String> getUniqueURLOutsideWebsite() {
		return uniqueURLOutsideWebsite;
	}

	public void setUniqueURLOutsideWebsite(String uniqueURLOutsideWebsite) {
		this.uniqueURLOutsideWebsite.add(uniqueURLOutsideWebsite);
	}

	public Set<String> getUniqueURLOutsideUSC() {
		return uniqueURLOutsideUSC;
	}

	public void setUniqueURLOutsideUSC(String uniqueURLOutsideUSC) {
		this.uniqueURLOutsideUSC.add(uniqueURLOutsideUSC);
	}

	public int getStatusOK() {
		return statusOK;
	}

	public void setStatusOK(int statusOK) {
		this.statusOK = statusOK;
	}

	public int getStatusMoved() {
		return statusMoved;
	}

	public void setStatusMoved(int statusMoved) {
		this.statusMoved = statusMoved;
	}

	public int getStatusUnauth() {
		return statusUnauth;
	}

	public void setStatusUnauth(int statusUnauth) {
		this.statusUnauth = statusUnauth;
	}

	public int getStatusForbidden() {
		return statusForbidden;
	}

	public void setStatusForbidden(int statusForbidden) {
		this.statusForbidden = statusForbidden;
	}

	public int getStatusNotFound() {
		return statusNotFound;
	}

	public void setStatusNotFound(int statusNotFound) {
		this.statusNotFound = statusNotFound;
	}

	public int getSize1() {
		return size1;
	}

	public void setSize1(int size1) {
		this.size1 = size1;
	}

	public int getSize2() {
		return size2;
	}

	public void setSize2(int size2) {
		this.size2 = size2;
	}

	public int getSize3() {
		return size3;
	}

	public void setSize3(int size3) {
		this.size3 = size3;
	}

	public int getSize4() {
		return size4;
	}

	public void setSize4(int size4) {
		this.size4 = size4;
	}

	public int getSize5() {
		return size5;
	}

	public void setSize5(int size5) {
		this.size5 = size5;
	}

	public int getTypeText() {
		return typeText;
	}

	public void setTypeText(int typeText) {
		this.typeText = typeText;
	}

	public int getTypeGif() {
		return typeGif;
	}

	public void setTypeGif(int typeGif) {
		this.typeGif = typeGif;
	}

	public int getTypePng() {
		return typePng;
	}

	public void setTypePng(int typePng) {
		this.typePng = typePng;
	}

	public int getTypePdf() {
		return typePdf;
	}

	public void setTypePdf(int typePdf) {
		this.typePdf = typePdf;
	}public int getTypeJpeg() {
		return typeJpeg;
	}

	public void setTypeJpeg(int typeJpeg) {
		this.typeJpeg = typeJpeg;
	}

	public int getTypeDoc() {
		return typeDoc;
	}

	public void setTypeDoc(int typeDoc) {
		this.typeDoc = typeDoc;
	}
}
