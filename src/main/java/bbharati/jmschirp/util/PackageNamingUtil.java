package bbharati.jmschirp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 17/07/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 * Ref: http://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html,
 * http://stackoverflow.com/questions/2125293/java-packages-com-and-org,
 * http://en.wikipedia.org/wiki/List_of_Internet_top-level_domains
 *
 */
public class PackageNamingUtil {

    private static List<String> countryCodeDomains;
    private static List<String> genericTopLevelDomains;
    public static List<String> javaPkgDomains;

    static {
        countryCodeDomains = new ArrayList<String>();
        countryCodeDomains.add("ac");
        countryCodeDomains.add("ad");
        countryCodeDomains.add("ae");
        countryCodeDomains.add("af");
        countryCodeDomains.add("ag");
        countryCodeDomains.add("ai");
        countryCodeDomains.add("al");
        countryCodeDomains.add("am");
        countryCodeDomains.add("an");
        countryCodeDomains.add("ao");
        countryCodeDomains.add("aq");
        countryCodeDomains.add("ar");
        countryCodeDomains.add("as");
        countryCodeDomains.add("at");
        countryCodeDomains.add("au");
        countryCodeDomains.add("aw");
        countryCodeDomains.add("ax");
        countryCodeDomains.add("az");
        countryCodeDomains.add("ba");
        countryCodeDomains.add("bb");
        countryCodeDomains.add("bd");
        countryCodeDomains.add("be");
        countryCodeDomains.add("bf");
        countryCodeDomains.add("bg");
        countryCodeDomains.add("bh");
        countryCodeDomains.add("bi");
        countryCodeDomains.add("bj");
        countryCodeDomains.add("bm");
        countryCodeDomains.add("bn");
        countryCodeDomains.add("bo");
        countryCodeDomains.add("bs");
        countryCodeDomains.add("bt");
        countryCodeDomains.add("bv");
        countryCodeDomains.add("bw");
        countryCodeDomains.add("by");
        countryCodeDomains.add("bz");
        countryCodeDomains.add("ca");
        countryCodeDomains.add("cc");
        countryCodeDomains.add("cd");
        countryCodeDomains.add("cf");
        countryCodeDomains.add("cg");
        countryCodeDomains.add("ch");
        countryCodeDomains.add("ci");
        countryCodeDomains.add("ck");
        countryCodeDomains.add("cl");
        countryCodeDomains.add("cm");
        countryCodeDomains.add("cn");
        countryCodeDomains.add("co");
        countryCodeDomains.add("cr");
        countryCodeDomains.add("cs");
        countryCodeDomains.add("cu");
        countryCodeDomains.add("cv");
        countryCodeDomains.add("cx");
        countryCodeDomains.add("cy");
        countryCodeDomains.add("cz");
        countryCodeDomains.add("dd");
        countryCodeDomains.add("de");
        countryCodeDomains.add("dj");
        countryCodeDomains.add("dk");
        countryCodeDomains.add("dm");
        countryCodeDomains.add("do");
        countryCodeDomains.add("dz");
        countryCodeDomains.add("ec");
        countryCodeDomains.add("ee");
        countryCodeDomains.add("eg");
        countryCodeDomains.add("eh");
        countryCodeDomains.add("er");
        countryCodeDomains.add("es");
        countryCodeDomains.add("et");
        countryCodeDomains.add("eu");
        countryCodeDomains.add("fi");
        countryCodeDomains.add("fj");
        countryCodeDomains.add("fk");
        countryCodeDomains.add("fm");
        countryCodeDomains.add("fo");
        countryCodeDomains.add("fr");
        countryCodeDomains.add("ga");
        countryCodeDomains.add("gb");
        countryCodeDomains.add("gd");
        countryCodeDomains.add("ge");
        countryCodeDomains.add("gf");
        countryCodeDomains.add("gg");
        countryCodeDomains.add("gh");
        countryCodeDomains.add("gl");
        countryCodeDomains.add("gm");
        countryCodeDomains.add("gn");
        countryCodeDomains.add("gp");
        countryCodeDomains.add("gq");
        countryCodeDomains.add("gr");
        countryCodeDomains.add("gs");
        countryCodeDomains.add("gt");
        countryCodeDomains.add("gu");
        countryCodeDomains.add("gw");
        countryCodeDomains.add("gy");
        countryCodeDomains.add("hk");
        countryCodeDomains.add("hm");
        countryCodeDomains.add("hn");
        countryCodeDomains.add("hr");
        countryCodeDomains.add("ht");
        countryCodeDomains.add("hu");
        countryCodeDomains.add("id");
        countryCodeDomains.add("ie");
        countryCodeDomains.add("il");
        countryCodeDomains.add("im");
        countryCodeDomains.add("in");
        countryCodeDomains.add("io");
        countryCodeDomains.add("iq");
        countryCodeDomains.add("ir");
        countryCodeDomains.add("is");
        countryCodeDomains.add("it");
        countryCodeDomains.add("je");
        countryCodeDomains.add("jm");
        countryCodeDomains.add("jo");
        countryCodeDomains.add("jp");
        countryCodeDomains.add("ke");
        countryCodeDomains.add("kg");
        countryCodeDomains.add("kh");
        countryCodeDomains.add("ki");
        countryCodeDomains.add("km");
        countryCodeDomains.add("kn");
        countryCodeDomains.add("kp");
        countryCodeDomains.add("kr");
        countryCodeDomains.add("kw");
        countryCodeDomains.add("ky");
        countryCodeDomains.add("kz");
        countryCodeDomains.add("la");
        countryCodeDomains.add("lb");
        countryCodeDomains.add("lc");
        countryCodeDomains.add("li");
        countryCodeDomains.add("lk");
        countryCodeDomains.add("lr");
        countryCodeDomains.add("lt");
        countryCodeDomains.add("lu");
        countryCodeDomains.add("lv");
        countryCodeDomains.add("ly");
        countryCodeDomains.add("ma");
        countryCodeDomains.add("mc");
        countryCodeDomains.add("md");
        countryCodeDomains.add("me");
        countryCodeDomains.add("mg");
        countryCodeDomains.add("mh");
        countryCodeDomains.add("mk");
        countryCodeDomains.add("ml");
        countryCodeDomains.add("mm");
        countryCodeDomains.add("mn");
        countryCodeDomains.add("mo");
        countryCodeDomains.add("mp");
        countryCodeDomains.add("mq");
        countryCodeDomains.add("mr");
        countryCodeDomains.add("ms");
        countryCodeDomains.add("mt");
        countryCodeDomains.add("mu");
        countryCodeDomains.add("mv");
        countryCodeDomains.add("mw");
        countryCodeDomains.add("mx");
        countryCodeDomains.add("my");
        countryCodeDomains.add("mz");
        countryCodeDomains.add("na");
        countryCodeDomains.add("nc");
        countryCodeDomains.add("ne");
        countryCodeDomains.add("nf");
        countryCodeDomains.add("ng");
        countryCodeDomains.add("ni");
        countryCodeDomains.add("nl");
        countryCodeDomains.add("no");
        countryCodeDomains.add("np");
        countryCodeDomains.add("nr");
        countryCodeDomains.add("nu");
        countryCodeDomains.add("nz");
        countryCodeDomains.add("om");
        countryCodeDomains.add("cg");
        countryCodeDomains.add("pa");
        countryCodeDomains.add("pe");
        countryCodeDomains.add("pf");
        countryCodeDomains.add("pg");
        countryCodeDomains.add("ph");
        countryCodeDomains.add("pk");
        countryCodeDomains.add("pl");
        countryCodeDomains.add("pm");
        countryCodeDomains.add("pn");
        countryCodeDomains.add("pr");
        countryCodeDomains.add("ps");
        countryCodeDomains.add("pt");
        countryCodeDomains.add("pw");
        countryCodeDomains.add("py");
        countryCodeDomains.add("qa");
        countryCodeDomains.add("re");
        countryCodeDomains.add("ro");
        countryCodeDomains.add("rs");
        countryCodeDomains.add("ru");
        countryCodeDomains.add("rw");
        countryCodeDomains.add("sa");
        countryCodeDomains.add("sb");
        countryCodeDomains.add("sc");
        countryCodeDomains.add("sd");
        countryCodeDomains.add("se");
        countryCodeDomains.add("sg");
        countryCodeDomains.add("sh");
        countryCodeDomains.add("si");
        countryCodeDomains.add("sj");
        countryCodeDomains.add("sk");
        countryCodeDomains.add("sl");
        countryCodeDomains.add("sm");
        countryCodeDomains.add("sn");
        countryCodeDomains.add("so");
        countryCodeDomains.add("sr");
        countryCodeDomains.add("ss");
        countryCodeDomains.add("st");
        countryCodeDomains.add("su");
        countryCodeDomains.add("sv");
        countryCodeDomains.add("sx");
        countryCodeDomains.add("sy");
        countryCodeDomains.add("tc");
        countryCodeDomains.add("td");
        countryCodeDomains.add("tf");
        countryCodeDomains.add("tg");
        countryCodeDomains.add("th");
        countryCodeDomains.add("tj");
        countryCodeDomains.add("tk");
        countryCodeDomains.add("tl");
        countryCodeDomains.add("tm");
        countryCodeDomains.add("tn");
        countryCodeDomains.add("to");
        countryCodeDomains.add("tp");
        countryCodeDomains.add("tr");
        countryCodeDomains.add("tv");
        countryCodeDomains.add("tw");
        countryCodeDomains.add("tz");
        countryCodeDomains.add("ua");
        countryCodeDomains.add("ug");
        countryCodeDomains.add("uk");
        countryCodeDomains.add("us");
        countryCodeDomains.add("uy");
        countryCodeDomains.add("uz");
        countryCodeDomains.add("va");
        countryCodeDomains.add("vc");
        countryCodeDomains.add("ve");
        countryCodeDomains.add("vg");
        countryCodeDomains.add("vi");
        countryCodeDomains.add("vn");
        countryCodeDomains.add("vu");
        countryCodeDomains.add("wf");
        countryCodeDomains.add("ws");
        countryCodeDomains.add("ye");
        countryCodeDomains.add("yt");
        countryCodeDomains.add("yu");
        countryCodeDomains.add("za");
        countryCodeDomains.add("zm");
        countryCodeDomains.add("zw");

        genericTopLevelDomains = new ArrayList<String>();
        genericTopLevelDomains.add("aero");
        genericTopLevelDomains.add("asia");
        genericTopLevelDomains.add("biz");
        genericTopLevelDomains.add("cat");
        genericTopLevelDomains.add("com");
        genericTopLevelDomains.add("coop");
        genericTopLevelDomains.add("info");
        genericTopLevelDomains.add("int");
        genericTopLevelDomains.add("jobs");
        genericTopLevelDomains.add("mobi");
        genericTopLevelDomains.add("museum");
        genericTopLevelDomains.add("name");
        genericTopLevelDomains.add("net");
        genericTopLevelDomains.add("org");
        genericTopLevelDomains.add("post");
        genericTopLevelDomains.add("pro");
        genericTopLevelDomains.add("tel");
        genericTopLevelDomains.add("travel");
        genericTopLevelDomains.add("xxx");

        genericTopLevelDomains.add("edu");
        genericTopLevelDomains.add("gov");
        genericTopLevelDomains.add("mil");

        javaPkgDomains = new ArrayList<String>();
        javaPkgDomains.add("java");


    }

    public static String getTopLevelPackages(String className)
    {
        String topPkgName = null;

        int firstDotIdx = className.indexOf(".") ;
        int secondDotIdx = -1;
        int thirdDotIdx = -1;

        boolean startsWithCountryDomain = countryCodeDomains.contains(className.substring(0, firstDotIdx));
        boolean startsWitheGenericDomain =   genericTopLevelDomains.contains(className.substring(0, firstDotIdx));



            if( startsWithCountryDomain || startsWitheGenericDomain)
            {

                if(firstDotIdx != -1)
                {
                    secondDotIdx = className.indexOf(".", firstDotIdx+1);
                    if(secondDotIdx != -1)
                    {
                        if(startsWithCountryDomain)  //Check for country level pkg names, something like - in.com.cofeeday / in.coffeday
                        {
                            String secondPkgToken =  className.substring(firstDotIdx, secondDotIdx) ;
                            if(secondPkgToken != null && genericTopLevelDomains.contains(secondPkgToken))  //in.com.cofeeday
                            {
                                thirdDotIdx = className.indexOf(".", secondDotIdx+1);
                                if(thirdDotIdx != -1)
                                {
                                    topPkgName = className.substring(0, thirdDotIdx);
                                }
                            }
                            else
                            {
                                topPkgName = className.substring(0, secondDotIdx);
                            }

                        }
                        else if(startsWitheGenericDomain)
                        {
                            topPkgName = className.substring(0, secondDotIdx);
                        }

                    }
                }


            }
            else //Someone's funny package name that doesn't comply to standards
            {
                topPkgName = className.substring(0, firstDotIdx);
            }

         return topPkgName;
    }


}
