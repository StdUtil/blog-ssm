package xyz.stackoverflow.blog.web.controller.front;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;
import xyz.stackoverflow.blog.pojo.entity.Article;
import xyz.stackoverflow.blog.pojo.entity.Category;
import xyz.stackoverflow.blog.pojo.vo.ArticleVO;
import xyz.stackoverflow.blog.pojo.vo.CategoryVO;
import xyz.stackoverflow.blog.service.ArticleService;
import xyz.stackoverflow.blog.service.CategoryService;
import xyz.stackoverflow.blog.service.CommentService;
import xyz.stackoverflow.blog.service.UserService;
import xyz.stackoverflow.blog.util.DateUtil;
import xyz.stackoverflow.blog.util.db.PageParameter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 前端分类页面跳转控制器
 *
 * @author 凉衫薄
 */
@Controller
public class CategoryPageController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    /**
     * 跳转到某一分类所有文章显示页面 /category/{categoryCode}
     * 方法 GET
     *
     * @return 返回ModelAndView, 查找成功时返回分类页面, 否则返回404页面
     */
    @RequestMapping(value = "/category/{categoryCode}", method = RequestMethod.GET)
    public ModelAndView categoryArticle(@PathVariable("categoryCode") String categoryCode, @RequestParam(value = "page", required = false, defaultValue = "1") String page, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        ServletContext application = request.getServletContext();
        Map<String, Object> settingMap = (Map<String, Object>) application.getAttribute("setting");
        int limit = Integer.valueOf((String) settingMap.get("limit"));

        Category category = categoryService.getCategoryByCode(categoryCode);
        if (category != null) {
            PageParameter parameter = new PageParameter(Integer.valueOf(page), limit, category.getId());
            List<Article> articleList = articleService.getLimitVisibleArticleByCategoryId(parameter);
            List<ArticleVO> articleVOList = new ArrayList<>();
            for (Article article : articleList) {
                ArticleVO vo = new ArticleVO();
                vo.setTitle(HtmlUtils.htmlEscape(article.getTitle()));
                vo.setAuthor(HtmlUtils.htmlEscape(userService.getUserById(article.getUserId()).getNickname()));
                vo.setCategoryName(categoryService.getCategoryById(article.getCategoryId()).getCategoryName());
                vo.setCommentCount(commentService.getCommentCountByArticleId(article.getId()));
                vo.setHits(article.getHits());
                vo.setLikes(article.getLikes());
                vo.setPreview(Jsoup.parse(article.getArticleHtml()).text());
                vo.setUrl(article.getUrl());
                vo.setCreateDateString(DateUtil.formatDate(article.getCreateDate()));
                articleVOList.add(vo);
            }

            int count = articleService.getVisibleArticleCountByCategoryId(category.getId());
            int pageCount = (count % limit == 0) ? count / limit : count / limit + 1;
            pageCount = pageCount == 0 ? 1 : pageCount;
            int start = (Integer.valueOf(page) - 2 < 1) ? 1 : Integer.valueOf(page) - 2;
            int end = (start + 4 > pageCount) ? pageCount : start + 4;
            if ((end - start) < 4) {
                start = (end - 4 < 1) ? 1 : end - 4;
            }

            mv.addObject("articleList", articleVOList);
            mv.addObject("start", start);
            mv.addObject("end", end);
            mv.addObject("page", Integer.valueOf(page));
            mv.addObject("pageCount", pageCount);
            mv.addObject("path", "/category/" + categoryCode);
            mv.addObject("select", "/category");
            mv.addObject("header", category.getCategoryName());
            mv.setViewName("/index");
        } else {
            mv.setStatus(HttpStatus.NOT_FOUND);
            mv.setViewName("/error/404");
        }
        return mv;
    }

    /**
     * 分类页面跳转
     *
     * @return
     */
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ModelAndView category() {
        ModelAndView mv = new ModelAndView();

        List<Category> categoryList = categoryService.getAllCategory();
        List<CategoryVO> categoryVOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryVO vo = new CategoryVO();
            vo.setCategoryName(category.getCategoryName());
            vo.setCategoryCode(category.getCategoryCode());
            vo.setArticleCount(articleService.getVisibleArticleCountByCategoryId(category.getId()));
            categoryVOList.add(vo);
        }

        mv.addObject("categoryList", categoryVOList);
        mv.addObject("select", "/category");
        mv.setViewName("/category");
        return mv;
    }
}