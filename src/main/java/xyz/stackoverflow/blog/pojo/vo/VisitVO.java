package xyz.stackoverflow.blog.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import xyz.stackoverflow.blog.pojo.entity.Visit;
import xyz.stackoverflow.blog.util.web.SuperVO;

import java.util.Date;

/**
 * 访问量VO类
 *
 * @author 凉衫薄
 */
public class VisitVO implements SuperVO {

    private String id;
    private String url;
    private Integer status;
    private String ip;
    private String agent;
    private String referer;
    private Date date;

    public VisitVO() {

    }

    public VisitVO(String id, String url, Integer status, String ip, String agent, String referer, Date date) {
        this.id = id;
        this.url = url;
        this.status = status;
        this.ip = ip;
        this.agent = agent;
        this.referer = referer;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * VO类转实体类
     *
     * @return
     */
    public Visit toVisit() {
        Visit visit = new Visit();
        visit.setId(id);
        visit.setUrl(url);
        visit.setId(ip);
        visit.setAgent(agent);
        visit.setStatus(status);
        visit.setReferer(referer);
        visit.setDate(date);
        return visit;
    }
}
