package top.iotos.system.domain.vo;

import top.iotos.common.utils.StringUtils;

/**
 * 路由显示信息
 * 
 * @author iotos.top
 */
public class MetaVo
{
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    private boolean noCache;


    /* 国际化-对应code*/
    private String tcode;


    /**
     * 内链地址（http(s)://开头）
     */
    private String link;

    public MetaVo()
    {
    }

    public MetaVo(String title, String icon,String tcode)
    {
        this.title = title;
        this.icon = icon;
        this.tcode = tcode;
    }

    public MetaVo(String title, String icon, boolean noCache,String tcode)
    {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        this.tcode = tcode;
    }

    public MetaVo(String title, String icon, String link,String tcode)
    {
        this.title = title;
        this.icon = icon;
        this.link = link;
        this.tcode = tcode;
    }

    public MetaVo(String title, String icon, boolean noCache, String link,String tcode)
    {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        this.tcode = tcode;
        if (StringUtils.ishttp(link))
        {
            this.link = link;
        }
    }

    public boolean isNoCache()
    {
        return noCache;
    }

    public void setNoCache(boolean noCache)
    {
        this.noCache = noCache;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }


    public String getTcode()
    {
        return tcode;
    }

    public void setTcode(String tcode)
    {
        this.tcode = tcode;
    }


}
