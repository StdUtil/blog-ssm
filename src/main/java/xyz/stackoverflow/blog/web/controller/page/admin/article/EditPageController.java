package xyz.stackoverflow.blog.web.controller.page.admin.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import xyz.stackoverflow.blog.pojo.entity.Article;
import xyz.stackoverflow.blog.pojo.entity.Category;
import xyz.stackoverflow.blog.pojo.vo.ArticleVO;
import xyz.stackoverflow.blog.service.ArticleService;
import xyz.stackoverflow.blog.service.CategoryService;
import xyz.stackoverflow.blog.util.web.BaseController;

import java.util.HashMap;
import java.util.List;

/**
 * 后台管理系统文章编辑页面Controller
 *
 * @author 凉衫薄
 */
@Controller
@RequestMapping("/admin/article")
public class EditPageController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 通过文章url获取code
     *
     * @param url
     * @return
     */
    private String urlToCode(String url) {
        String[] list = url.split("/");
        return list[list.length - 1];
    }

    /**
     * 跳转到文章编辑页面 /admin/article/edit
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView article(@RequestParam(value = "id", required = false) String id) {
        ModelAndView mv = new ModelAndView();

        List<Category> categoryList = categoryService.selectByCondition(new HashMap<String, Object>());

        if (id != null) {
            Article article = articleService.selectById(id);
            ArticleVO articleVO = new ArticleVO();
            articleVO.setTitle(article.getTitle());
            articleVO.setArticleCode(urlToCode(article.getUrl()));
            articleVO.setArticleMd(article.getArticleMd());
            mv.addObject("selected", article.getCategoryId());
            mv.addObject("article", articleVO);
        } else {
            mv.addObject("selected", categoryService.selectByCondition(new HashMap<String, Object>() {{
                put("code", "uncategory");
            }}).get(0).getId());
        }

        mv.addObject("categoryList", categoryList);
        mv.setViewName("/admin/article/edit");

        return mv;
    }


}
