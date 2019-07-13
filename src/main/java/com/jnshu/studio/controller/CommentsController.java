package com.jnshu.studio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.jnshu.studio.model.Comments;
import com.jnshu.studio.model.Works;
import com.jnshu.studio.model.vo.CommentSearchVO;
import com.jnshu.studio.model.vo.CommentVO;
import com.jnshu.studio.service.CommentsService;
import com.jnshu.studio.service.WorksService;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/studio")
public class CommentsController {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    CommentsService commentsService;
    @Autowired
    WorksService worksService;

    /**
     * 查询第一个版本，已过期
     *
     * @param comments 留言
     * @param works    作品
     */
    @RequestMapping(value = "/backstage/comments", method = RequestMethod.GET)
    public Map<String, Object> selectComments(Comments comments, Works works) {
        /*创建map集合返回数据至前端*/
        Map<String, Object> map = new HashMap<>(16);
        try {
            /*查询作品*/
            List<Works> worksList = worksService.selectWorks(works);
            /*是否查询出了作品*/
            if (worksList != null) {
                map.put("worksList", worksList);
                /*通过作品ID查留言*/
                for (Works works1 : worksList) {
                    comments.setWorksId(works1.getId());
                    /*查询此作品下的有留言*/
                    List<Comments> commentsList1 = commentsService.selectComments(comments);
                    if (commentsList1 != null) {
                        map.put("worksCommentsList", commentsList1);
                        map.put("code", 1);
                        map.put("msg", "查询到留言");
                    } else {
                        map.put("code", -1);
                        map.put("msg", "查询到的作品没有留言");
                    }
                }
            } else {
                map.put("code", -1);
                map.put("msg", "无法查到作品");
            }
        } catch (Exception e) {
            map.put("code", -1);
            map.put("msg", "查询出错");
        }
        return map;
    }

    /**
     * 查询第三个版本，已过期
     *
     * @param comments       留言
     * @param works          作品
     * @param commentsStatus 留言状态
     * @param worksStatus    作品状态
     */
    @RequestMapping(value = "/backstage/comments/works", method = RequestMethod.GET)
    public Map selectCommentsWorks(Works works, Comments comments, Integer page, Integer size, Integer worksStatus, Integer commentsStatus) {
        /*创建返给前端的map集合*/
        Map<String, Object> map = new HashMap<>();
        List result = new ArrayList();
        try {
            /*当查询状态时，用来区别是作品还是留言的状态*/
            works.setStatus(worksStatus);
            comments.setStatus(commentsStatus);
            /*模糊查询所有作品，即查询作品标题*/
            List<Works> worksList = worksService.selectWorks(works);
            /*判断是否查询出数据*/
            Validate.notEmpty(worksList);
            /*创建一个VO集合*/
            List<CommentVO> commentVoList = new ArrayList<>();
            /*遍历查询出的作品*/
            for (Works works1 : worksList) {
                logger.info("测试{}", works1.getId());
                /*将作品ID赋值给留言的worksId */
                comments.setWorksId(works1.getId());
                comments.setCommentsId(0L);
                logger.info("测的对象{}", comments);
                /*页数与行数为空时默认值*/
                if (page == null || size == null) {
                    page = 1;
                    size = 10;
                }
                /*页数，排序规则设置*/
                String orderBy = "status desc,update_at desc";
                /*传入页数、行数、排序规则*/
                PageHelper.startPage(page, size, orderBy);
                /*查询所有留言,如果传了状态值，将增加状态值查询*/
                List<Comments> commentsList = commentsService.selectComments(comments);
                logger.info("测试出的{}", commentsList);
                /*遍历留言集合,且判断记录是否满足条件*/
                for (Comments comments1 : commentsList) {
                    if (comments1.getId() != null) {
                        /*创建VO类进行前端的显示*/
                        CommentVO commentVO = new CommentVO();
                        /*将标题赋值给VO类*/
                        commentVO.setWorkName(works1.getTitle());
                        /*将留言复制到VO类*/
                        BeanUtils.copyProperties(commentsList.get(0), commentVO);
                        /*添加到结果集合*/
                        commentVoList.add(commentVO);
                    }
                }
            }

            map.put("data", commentVoList);
            map.put("code", 1);
            map.put("msg", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("msg", "操作失败");
        }
        return map;
    }


    /**
     * 查询第二个版本，已过期
     *
     * @param comments 留言表
     * @param page     当前页数
     * @param size     当前页记录数
     */

    @RequestMapping(value = "/backstage/comments/all", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String selectCommentsAll(Comments comments, Integer page, Integer size) {
        /*创建Gson对象*/
        Gson gson = new Gson();
        /*创建VO类对象集合*/
        List<CommentVO> result = new ArrayList<>();
        /*创建map集合保存code，msg与data*/
        Map<String, Object> map = new HashMap<>(16);
        try {
            /*默认只返回全部游客给作品的留言*/
            comments.setCommentsId(0L);
            /*设置分页插件与赋初值*/
            if (page == null || size == null) {
                page = 1;
                size = 10;
            }
            /*设置分页插件排序规则*/
            String orderBy = "status desc,update_at desc";
            /*设值分页的值*/
            PageHelper.startPage(page, size, orderBy);
            /*查询所有游客留言*/
            List<Comments> list = commentsService.selectComments(comments);
            /*通过遍历出的游客留言查询作品信息*/
            for (Comments item : list) {
                /*1.根据作品id查作品信息*/
                /*创建vo类对象*/
                CommentVO commentVO = new CommentVO();
                /*将查询到的对象复制到VO类对象中*/
                BeanUtils.copyProperties(item, commentVO);
                /*创建作品类对象*/
                Works works1 = new Works();
                /*给作品ID赋值*/
                works1.setId(item.getWorksId());
                /*保存作品到集合*/
                List<Works> works = worksService.selectWorks(works1);
                logger.info("作品集{}", works);
                /*判断作品集合是否不为空*/
                if (!works.isEmpty()) {
                    /*获取作品集合第一条数据的标题给VO类对象*/
                    commentVO.setWorkName(works.get(0).getTitle());
                }
                /*将VO对象添加至结果集合*/
                result.add(commentVO);
            }
            logger.info("结果集{}", result);
            map.put("data", result);
            map.put("code", 1);
            map.put("msg", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("msg", "操作失败");
        }
        /*返回map集合*/
        return gson.toJson(map);
    }

    /**
     * 查询第四个版本，使用中，此为留言的列表查询
     * 使用单条SQL关联查询留言的接口
     *
     * @param title          作品标题
     * @param commentsStatus 留言状态
     * @param page           页数
     * @param size           记录数
     */
    @RequestMapping(value = "/backstage/comments/refactoring", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String selectCommentsRefactoring(String title, Integer commentsStatus, Integer page, Integer size) {
        /*创建返给前端的map集合*/
        Map<String, Object> result = new HashMap<>(16);
        /*data集合*/
        Map<String, Object> data = new HashMap<>(16);
        try {
            /*创建入参VO类*/
            CommentSearchVO commentSearchVO = new CommentSearchVO();
            /*入参*/
            commentSearchVO.setWorkName(title);
            commentSearchVO.setStatus(commentsStatus);
            /*设置要查询的留言，0为单纯给作品留言的*/
            commentSearchVO.setCommentsId(0L);
            /*设置分页插件与赋初值*/
            if (page == null || size == null) {
                page = 1;
                size = 10;
            }
            /*设置分页插件排序规则*/
            String orderBy = "comments.update_at desc";
            /*设值分页的值*/
            PageHelper.startPage(page, size, orderBy);
            logger.info("要查的对象{}", commentSearchVO);
            /*查询集合*/
            List<CommentSearchVO> commentSearchVOList = commentsService.selectCommentsWorks(commentSearchVO);
            PageInfo pageInfo = new PageInfo(commentSearchVOList);
            /*要添加的data集合内容*/
            data.put("page", pageInfo.getPages());
            data.put("size", pageInfo.getSize());
            data.put("commentWorksList", commentSearchVOList);

            result.put("code", 1);
            result.put("msg", "查询成功");
            result.put("data", data);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "查询失败");
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * 留言详细查询与查询回复
     */
    @RequestMapping(value = "/backstage/comments/id", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String selectId(Long id, Long commentsId) {
        /*创建返给前端的map集合*/
        Map<String, Object> result = new HashMap<>(16);
        /*data集合*/
        Map<String, Object> data = new HashMap<>(16);
        try {
            logger.info("传入的id:{}或commentsID:{}", id, commentsId);
            /*判断是给作品的留言还是给留言的回复*/
            if (id != null && commentsId == null) {
                /*根据ID查询作品留言*/
                data.put("comments", commentsService.selectByPrimaryKey(id));
            } else if (commentsId != null && id == null) {
                Comments comments = new Comments();
                comments.setCommentsId(commentsId);
                /*根据commentsId查询所有回复*/
                data.put("commentsList", commentsService.selectComments(comments));
            }
            result.put("data", data);
            result.put("code", 1);
            result.put("msg", "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "查询失败");
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }


    /**
     * 删除留言接口，只删除当前留言，未关联回复留言。
     *
     * @param id 留言ID
     */
    @RequestMapping(value = "/backstage/comments/refactoring", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public String selectCommentsRefactoring(@RequestBody long id) {
        /*创建返给前端的map集合*/
        Map<String, Object> result = new HashMap<>(16);
        try {
            logger.info("{}", id);
            /*校验删除是否成功，成功返回值为1*/
            Validate.isTrue(commentsService.deleteByPrimaryKey(id) == 1);
            result.put("code", 1);
            result.put("msg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "删除失败");
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * 前台游客留言接口
     *
     * @param comments 留言
     */
    @RequestMapping(value = "/comments", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String insert(@RequestBody Comments comments) {
        /*创建返给前端的map集合*/
        Map<String, Object> result = new HashMap<>(16);
        /*data集合*/
        Map<String, Object> data = new HashMap<>(16);
        try {
            /*新增数据时判断留言是否为空，新增时设置状态为下架状态与时间，CommentsId为0表示为作品的留言*/
            Validate.notNull(comments.getText());
            Validate.notNull(comments.getWorksId());
            comments.setCommentsId(0L);
            comments.setStatus(10);
            comments.setCreateAt(System.currentTimeMillis());
            comments.setUpdateAt(System.currentTimeMillis());
            commentsService.insertSelective(comments);
            /*新增成功后返回的新记录ID*/
            data.put("newId", comments.getId());
            result.put("data", data);
            result.put("code", 1);
            result.put("msg", "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 1);
            result.put("msg", "增加失败");
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * 后台新增回复接口
     *
     * @param comments 留言
     */
    @RequestMapping(value = "/backstage/comments/refactoring", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String insertBackstage(@RequestBody Comments comments) {
        /*创建返给前端的map集合*/
        Map<String, Object> result = new HashMap<>();
        /*data集合*/
        Map<String, Object> data = new HashMap<>();
        try {
            /*新增数据时判断留言内容是否为空*/
            Validate.notEmpty(comments.getText());
            /*给哪个留言回复的，前端必传入对应的留言ID：commentsId与作品ID*/
            Validate.notNull(comments.getCommentsId());
            Validate.notNull(comments.getWorksId());
            /*新增时设置状态为下架状态与时间*/
            comments.setStatus(10);
            comments.setCreateAt(System.currentTimeMillis());
            comments.setUpdateAt(System.currentTimeMillis());
            commentsService.insertSelective(comments);
            /*新增成功后返回的新记录ID*/
            data.put("newId", comments.getId());
            result.put("data", data);
            result.put("code", 1);
            result.put("msg", "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 1);
            result.put("msg", "增加失败");
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * .
     * 后台修改留言接口
     */
    @RequestMapping(value = "/backstage/comments/refactoring", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String updateByPrimaryKeySelective(@RequestBody Comments comments) {
        /*创建返给前端的map集合*/
        Map<String, Object> result = new HashMap<>();
        try {
            /*这里还应该自动获取修改人的名称。没做，嘿。*/
            Validate.notEmpty(comments.getText());
            comments.setUpdateAt(System.currentTimeMillis());
            Validate.isTrue(commentsService.updateByPrimaryKeySelective(comments) == 1);
            result.put("code", 1);
            result.put("msg", "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "更新失败");
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * 修改状态，设置精选
     * @param comments status 状态值 10为下架，20为上架，对应设置精选
     * */
    @RequestMapping(value = "/backstage/comments/refactoring/status", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String updateByPrimaryKeySelectiveStatus(@RequestBody Comments comments) {
        /*创建返给前端的map集合*/
        Map<String, Object> result = new HashMap<>(16);
        try {
            /*new对象，给对象状态属性赋值*/
            comments.setUpdateAt(System.currentTimeMillis());
            /*修改，判断是否修改成功*/
            Validate.isTrue(commentsService.updateByPrimaryKeySelective(comments)==1);
            result.put("code", 1);
            result.put("msg", "修改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "修改状态失败");
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }
}