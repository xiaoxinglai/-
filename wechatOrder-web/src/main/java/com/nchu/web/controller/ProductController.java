package com.nchu.web.controller;

import com.nchu.pojo.DO.ProductCategory;
import com.nchu.pojo.DO.ProductInfo;
import com.nchu.pojo.DO.SellerInfo;
import com.nchu.common.Form.BaseInfo;
import com.nchu.common.Result.PageResult;
import com.nchu.common.Enum.ProductEnum;
import com.nchu.common.Result.Result;
import com.nchu.common.Result.UploadResult;
import com.nchu.mapper.ex.ProductCategoryMapperEx;
import com.nchu.mapper.ex.ProductInfoMapperEx;
import com.nchu.product.service.IProductService;
import com.nchu.common.utils.KeyUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


@Controller
@RequestMapping("/product")
public class ProductController {
    @Value("${web.upload-path}")
    private String path;


    @Autowired
    private IProductService productService;
    @Autowired
    private ProductInfoMapperEx productInfoMapperEx;
    @Autowired
    private ProductCategoryMapperEx productCategoryMapperEx;
    /**
     * 查询所有菜品
     *
     * @param currPage
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryProduct", method = RequestMethod.GET)
    @ResponseBody
    public Object queryProduct(@RequestParam(value = "currPage", required = false) Integer currPage, HttpSession session, Model model) {

        BaseInfo baseInfo = (BaseInfo) session.getAttribute("baseInfo");
        if (ObjectUtils.isEmpty(currPage)) {
            currPage = 1;
        }

        PageResult pageResult = productService.queryProductInfoBysellId(currPage, baseInfo.getSellId());
        return pageResult;

    }


    /**
     * 查询指定菜品类型的所有菜品
     */
    @RequestMapping(value = "/queryProductByType", method = RequestMethod.GET)
    @ResponseBody
    public Object queryProductByType(@RequestParam(value = "currPage", required = false) Integer currPage, @RequestParam("typeId") Integer typeId, HttpSession session) {


        if (ObjectUtils.isEmpty(currPage)) {
            currPage = 1;
        }
        BaseInfo baseInfo = (BaseInfo) session.getAttribute("baseInfo");

        PageResult pageResult = productService.queryProductInfoBysellIdAndtypeId(currPage, typeId, baseInfo.getSellId());
        return pageResult;

    }


    /**
     * 进入指定菜品类型的页面
     */
    @RequestMapping(value = "/TypeIndex", method = RequestMethod.GET)
    public String TypeIndex(@RequestParam("typeId") String typeId, @RequestParam("Name") String name, Model model) {

        model.addAttribute("typeId", typeId);
        model.addAttribute("name", name);

        return "/TypeIndex";
    }

    /**
     * 查询指定商家的菜品类型
     */
    @RequestMapping(value = "/queryProductType", method = RequestMethod.GET)
    @ResponseBody
    public Object queryProductType(HttpSession session) {
        BaseInfo baseInfo = (BaseInfo) session.getAttribute("baseInfo");
        SellerInfo user = (SellerInfo) session.getAttribute("User");
        String SellId = null;
        if (baseInfo==null&&user==null){
            return "redirect:login";
        }else if(baseInfo != null) {
            SellId=baseInfo.getSellId();
        }else  if (user!=null){
            SellId=user.getSellId();
        }

        return productService.queryProductType(SellId);

    }


    /**
     * 进入菜品管理页面
     */
    @RequestMapping(value = "/productAdmin", method = RequestMethod.GET)
    public Object productAdmin(HttpSession session) {
        SellerInfo user = (SellerInfo) session.getAttribute("User");
        if (user == null) {
            return "redirect:/login";
        }


        return "/admin/ProducAdmin";
    }


    /**
     * 根据菜品名,菜品类型 查询指定商家的菜品
     */
    @RequestMapping(value = "/queryProductByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Object queryProductByCondition(@RequestParam(value = "currPage", required = false) Integer currPage, String name, String typeId,String Status, HttpSession session) {
        SellerInfo user = (SellerInfo) session.getAttribute("User");
        Integer type=null;
        Integer state=null;
        if (user == null) {
            return "redirect:/login";
        }

        if (name.equals("undefined") || name.equals("")) {
            name = null;
        }
        if (typeId.equals("undefined") || typeId.equals("")) {

        }else {

            type=Integer.parseInt(typeId);
        }

        if (Status==null||Status.equals("undefined") || Status.equals("")) {

        }else {

            state=Integer.parseInt(Status);
        }



        System.out.println(name + typeId+state);
        if (ObjectUtils.isEmpty(currPage)) {
            currPage = 1;
        }

        return productService.queryProductByCondition(currPage, name, type, user.getSellId(),state);

    }





    /**
     * 商家下架菜品
     */
    @RequestMapping(value = "/DownProduct", method = RequestMethod.GET)
    @ResponseBody
    public Integer DownProduct(@RequestParam(value = "prudctId", required=false) String prudctId,HttpSession session) {


        Result result=productService.UpOrDownProduct(prudctId,ProductEnum.DOWN.getCode());

        //成功则返回1 否则返回-1
        if (result.getSuccess()){
            return 1;
        }else {
            return -1;
        }
    }



    /**
     * 商家上架菜品
     */
    @RequestMapping(value = "/UpProduct", method = RequestMethod.GET)
    @ResponseBody
    public Integer UpProduct(@RequestParam(value = "prudctId", required=false) String prudctId,HttpSession session) {


        Result result=productService.UpOrDownProduct(prudctId,ProductEnum.UP.getCode());

        //成功则返回1 否则返回-1
        if (result.getSuccess()){
            return 1;
        }else {
            return -1;
        }
    }


    /**
     * 跳转到上传新菜品页面
     */
    @RequestMapping(value = "/ToUploadProduc",method = RequestMethod.GET)
    public String ToUploadProduc(HttpSession session){
        //todo权限认证

        return "/admin/addProduct";
    }


    /**
     * 处理上传的图片
     */
    /**
     * 单个文件
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public UploadResult handleFileUpload(MultipartFile file){
        UploadResult result=new UploadResult();
        if (!file.isEmpty()){
            Long time= LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            String fileName=time.toString()+file.getOriginalFilename();
            try {

                String url=path+fileName;
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(url)));

                out.write(file.getBytes());

                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                result.setCode(1);
                result.setMsg("上传失败"+e.getMessage());
                return result;
            } catch (IOException e) {

                result.setCode(1);
                result.setMsg("上传失败"+e.getMessage());
                return result;
            }

            result.setCode(0);
            result.setMsg("上传成功");
            result.setSrc("/uploadImg/"+fileName);
           return result;
        }else {
            result.setCode(1);
            result.setMsg("上传失败，因为文件是空的");
            return result;


        }


    }


    /**
     * 处理菜品上传请求
     *
     * @param productInfo
     * @param session
     * @return
     */
    @RequestMapping(value = "/doUploadProduct", method = RequestMethod.POST)
    @ResponseBody
    public int doUploadProduct(@RequestBody ProductInfo productInfo, HttpSession session) {

        SellerInfo user = (SellerInfo) session.getAttribute("User");
        productInfo.setSellId(user.getSellId());
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());
        String Id=KeyUtil.genProductUniqueKey();
        productInfo.setProductId(Id);
        int result=productInfoMapperEx.insertAll(productInfo);
        if (result>0) {
            return 1;
        } else {
            return -1;
        }
    }


    /**
     * 进入编辑菜品页面
     */
    @RequestMapping(value = "/toEditProduct",method = RequestMethod.GET)
    public String toEditProduct(@RequestParam("productId") String productId,Model model ){
      ProductInfo productInfo=  productInfoMapperEx.selectByPrimaryKey(productId);

        model.addAttribute("productInfo",productInfo);
        return "/admin/EditProduct";
    }

    /**
     * 处理编辑菜品
     */
    @RequestMapping(value = "/doEditProduct",method = RequestMethod.POST)
    @ResponseBody
    public Integer toEditProduct(@RequestBody ProductInfo productInfo,Model model,HttpSession session){
        SellerInfo user = (SellerInfo) session.getAttribute("User");
        ProductInfo oldInfo=  productInfoMapperEx.selectByPrimaryKey(productInfo.getProductId());
        productInfo.setCreateTime(oldInfo.getCreateTime());
        productInfo.setUpdateTime(new Date());
        productInfo.setSellId(user.getSellId());
        productInfoMapperEx.updateByPrimaryKey(productInfo);
        System.out.println(productInfo.toString());

        return 1;
    }


    /**
     * 进入菜品类型列表
     */
    @RequestMapping("/toProductType")
    public String toProductType(){

        return "/admin/ProductType";
    }


    /**
     * 查询指定商家的菜品类型带分页
     */
    @RequestMapping(value = "/queryProductTypeBypage", method = RequestMethod.GET)
    @ResponseBody
    public Object queryProductTypePage(HttpSession session) {
        BaseInfo baseInfo = (BaseInfo) session.getAttribute("baseInfo");
        SellerInfo user = (SellerInfo) session.getAttribute("User");
        String SellId = null;
        if (baseInfo==null&&user==null){
            return "redirect:login";
        }else if(baseInfo != null) {
            SellId=baseInfo.getSellId();
        }else  if (user!=null){
            SellId=user.getSellId();
        }

        return productService.queryProductTypePage(SellId);

    }


    /**
     * 删除指定id的菜品类型
     */
    @RequestMapping(value = "/DelProductType", method = RequestMethod.GET)
    @ResponseBody
    public Integer queryProductTypePage(@Param("categoryId") String categoryId, HttpSession session) {

        return productCategoryMapperEx.deleteByPrimaryKey(Integer.parseInt(categoryId));

    }

    /**
     * 更改指定id的菜品类型名字
     */
    @RequestMapping(value = "/UpdateProductType", method = RequestMethod.GET)
    @ResponseBody
    public Integer UpdateProductType(@Param("categoryId") String categoryId,@Param("categoryName") String categoryName, HttpSession session) {
        ProductCategory productCategory= productCategoryMapperEx.selectByPrimaryKey(Integer.parseInt(categoryId));
        productCategory.setCategoryName(categoryName);
        productCategory.setUpdateTime(new Date());

        return productCategoryMapperEx.updateByPrimaryKey(productCategory);

    }

    /**
     * 增加菜品类型
     */
    @RequestMapping(value = "/AddProductType", method = RequestMethod.POST)
    public String AddProductType(ProductCategory productCategory, HttpSession session) {

        SellerInfo user = (SellerInfo) session.getAttribute("User");
        productCategory.setSellId(user.getSellId());
        productCategory.setUpdateTime(new Date());
        productCategory.setCreateTime(new Date());
        productCategory.setCategoryId(KeyUtil.genProducTypetUniqueKey());
        productCategoryMapperEx.insertAll(productCategory);

        return "redirect:/product/toProductType";

    }


}


