package basic.arch.tools.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 内部类，实现了邮件内容的公共方法，包括地址和发送次数
 * @author caoxl
 */
public abstract class MailMessageSupport implements MailMessage{

	static final Logger logger =LoggerFactory.getLogger(MailMessageSupport.class);
	
	private List<String> toList; 
	private List<String> ccList;
	private List<String> bccList;
	int sendCount = 0;
	
	/**
	 * 返回邮件的发送地址，支持多个，用空格或者逗号或者分号隔开
	 * @return
	 */
	public InternetAddress[] getToAddress(){
		if(!hasTo()){
			return null;
		}
		InternetAddress[] address = new InternetAddress[toList.size()];
		String a;
		for(int i=0;i<toList.size();i++){
			a = toList.get(i);
			try {
				address[i] = new InternetAddress(a);
			} catch (AddressException e) {
				logger.error("返回邮件发送地址出现异常："+e);
			}
		}
        return address;
	}
	/**
	 * 返回邮件的抄送地址，支持多个，用空格或者逗号或者分号隔开
	 * @return
	 */
	public InternetAddress[] getCCAddress(){
		if(!hasCc()){
			return null;
		}
		InternetAddress[] address = new InternetAddress[ccList.size()];
		String a;
		for(int i=0;i<ccList.size();i++){
			a = ccList.get(i);
			try {
				address[i] = new InternetAddress(a);
			} catch (AddressException e) {
				logger.error("返回邮件抄送地址出现异常："+e);
			}
		}
        return address;
	}
	/**
	 * 返回邮件的暗送地址，支持多个，用空格或者逗号或者分号隔开
	 * @return
	 */
	public InternetAddress[] getBCCAddress(){
		if(!hasBcc()){
			return null;
		}
		InternetAddress[] address = new InternetAddress[bccList.size()];
		String a;
		for(int i=0;i<bccList.size();i++){
			a = bccList.get(i);
			try {
				address[i] = new InternetAddress(a);
			} catch (AddressException e) {
				logger.error("返回邮件暗送地址出现异常："+e);
			}
		}
        return address;
	}

	/**
	 * @return 发送对象email地址
	 */
	public String getTo() {
		if(!hasTo()){
			return null;
		}
		StringBuffer to = new StringBuffer();
		for(String a : toList){
			to.append(a).append(" ");
		}
		return to.toString();
	}
	
	/**
	 * @param 发送对象email地址
	 */
	public void setTo(String to) {
		String[] addressList = splitMailAddress(to);
		if(addressList==null || addressList.length==0){
			return;
		}
		//判断集合是否为空
		if(toList == null){
			toList = new ArrayList<String>();
		}
		//将拆分的地址加入集合中
		for(String a : addressList){
			toList.add(a);
		}
	}
	/**
	 * @return the 抄送对象email地址
	 */
	public String getCc() {
		if(!hasCc()){
			return null;
		}
		StringBuffer cc = new StringBuffer();
		for(String a : ccList){
			cc.append(a).append(" ");
		}
		return cc.toString();
	}
	/**
	 * @param 抄送对象email地址
	 */
	public void setCc(String cc) {
		String[] addressList = splitMailAddress(cc);
		if(addressList==null || addressList.length==0){
			return;
		}
		//判断集合是否为空
		if(ccList == null){
			ccList = new ArrayList<String>();
		}
		//将拆分的地址加入集合中
		for(String a : addressList){
			ccList.add(a);
		}
	}
	/**
	 * @return 暗送对象email地址
	 */
	
	public String getBcc() {
		if(!hasBcc()){
			return null;
		}
		StringBuffer bcc = new StringBuffer();
		for(String a : bccList){
			bcc.append(a).append(" ");
		}
		return bcc.toString();
	}
	/**
	 * @param 暗送对象email地址
	 */
	public void setBcc(String bcc) {
		String[] addressList = splitMailAddress(bcc);
		if(addressList==null || addressList.length==0){
			return;
		}
		//判断集合是否为空
		if(bccList == null){
			bccList = new ArrayList<String>();
		}
		//将拆分的地址加入集合中
		for(String a : addressList){
			bccList.add(a);
		}
	}
	/**
	 * 返回是否含有发送地址
	 * @return
	 */
	public boolean hasTo(){
		if(logger.isDebugEnabled()){
			logger.debug("hasTo="+toList);
		}
		return (toList != null && !toList.isEmpty());
	}
	/**
	 * 返回是否含有抄送地址
	 * @return
	 */
	public boolean hasCc(){
		return (ccList != null && !ccList.isEmpty());
	}
	/**
	 * 返回是否含有暗送地址
	 * @return
	 */
	public boolean hasBcc(){
		return (bccList != null && !bccList.isEmpty());
	}
	/**
	 * @return 发送次数
	 */
	public int getSendCount() {
		return sendCount;
	}
	/**
	 * @param 发送次数
	 */
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	
	/**
	 * 增加一次发送次数
	 */
	public void addSendCount(){
		sendCount++;
	}
	
	/**
	 * 判断是否大于最大发送次数
	 * @param maxCount 最大发送次数
	 * @return 是否大于最大发送次数
	 */
	public boolean isMaxSendCount(int maxCount){
		return sendCount>maxCount;
	}
	
	/**
	 * 将邮件地址拆分成List
	 * @param address 地址，多个地址中间用空格、逗号或者分号分隔
	 * @return 
	 */
	private String[] splitMailAddress(String address){
		if(address == null || address.trim().equals("")){
			return new String[0];
		}
		//拆分地址
		String[] addressList = null;
		if(address.indexOf(" ") > -1){
			addressList = address.split(" ");
		}else if(address.indexOf(",") > -1){
			addressList = address.split(",");
		}else  if(address.indexOf(";") > -1){
			addressList = address.split(";");
		}else {
			addressList = new String[1];
			addressList[0] = address;
		}
		return addressList;
	}
}
