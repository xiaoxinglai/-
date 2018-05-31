/**  
 * All rights Reserved, Designed By www.maihaoche.com
 * 
 * @Package com.mhc.arcfox.core.base.config
 * @author: 三帝（sandi@maihaoche.com）
 * @date:   2017年11月19日 下午10:22:14
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved. 
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */

package com.nchu.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**   
 * <p> dubbo配置 </p>
 *   
 * @author: 三帝（sandi@maihaoche.com） 
 * @date: 2017年11月19日 下午10:22:14   
 * @since V1.0 
 */
@Configuration
@ImportResource("classpath:dubbo/dubbo-all.xml")
public class DubboConfig {

}
