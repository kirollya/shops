package pers.nico.reports;

import pers.nico.models.entities.Item;
import pers.nico.models.entities.Sell;
import pers.nico.models.entities.Shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportBuilder {

    private List<Sell> sellList;
    private List<Shop> shopList;
    private List<Item> itemList;

    public ReportBuilder(List<Sell> sellList, List<Shop> shopList, List<Item> itemList) {
        this.sellList = sellList.stream().sorted(new Comparator<Sell>() {
            @Override
            public int compare(Sell o1, Sell o2) {
                return o1.getCount() - o2.getCount();
            }
        }).collect(Collectors.toList());
        this.shopList = shopList.stream().sorted(new Comparator<Shop>() {
            @Override
            public int compare(Shop o1, Shop o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }).collect(Collectors.toList());
        this.itemList = itemList;
    }

    public boolean build(Long id) {
        try {
            switch (id.intValue()) {
                case 1:
                    buildLineReport();
                    break;
                case 2:
                    buildTableReport();
                    break;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void buildLineReport() throws IOException {
        FileInputStream fis = new FileInputStream(".\\src\\main\\resources\\reports\\template.html");
        StringBuilder input = new StringBuilder("");
        byte ch = (byte)fis.read();
        while (ch >= 0) {
            input.append((char) ch);
            ch = (byte)fis.read();
        }
        fis.close();

        for (Shop shop: shopList) {
            input.append("<table>\n");
            input.append("<tr>\n");
            input.append("<th>Магазин:</th>\n<th colspan=\"2\">" + shop.getName() + "</th>\n");
            input.append("</tr>\n");
            input.append("<tr>\n");
            input.append("<th>" + shop.getAddress() + "</th>\n<th colspan=\"2\">" + shop.getPhone() + "</th>\n");
            input.append("</tr>\n");
            Integer sum = 0;
            for (Sell sell: sellList) {
                if (sell.getShop().equals(shop)) {
                    input.append("<tr>\n");
                    input.append("<th>" + sell.getItem().getName() + "</th>\n");
                    input.append("<th>" + sell.getCount() + "</th>\n");
                    input.append("<th>" + sell.getCost() + "</th>\n");
                    input.append("</tr>\n");
                    sum += sell.getCount();
                }
            }
            input.append("<tr>\n");
            input.append("<th>Итого: </th>\n<th colspan=\"2\">" + sum + "</th>\n");
            input.append("</tr>\n");
            input.append("</table>\n");
        }

        input.append("</body>\n</html>");
        FileOutputStream fos = new FileOutputStream(".\\src\\main\\resources\\reports\\lineReport.html");
        fos.write(input.toString().getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    private void buildTableReport() throws IOException {
        FileInputStream fis = new FileInputStream(".\\src\\main\\resources\\reports\\template.html");
        StringBuilder input = new StringBuilder("");
        byte ch = (byte)fis.read();
        while (ch >= 0) {
            input.append((char) ch);
            ch = (byte)fis.read();
        }
        fis.close();
        input.append("<table>");
        input.append("<tr>\n");
        input.append("<th></th>\n");
        for (Item item: itemList) {
            input.append("<th>" + item.getName() + "</th>\n");
        }
        input.append("</tr>\n");
        Integer[] itemCount = new Integer[itemList.size()];
        for (int i = 0; i < itemCount.length; i++)
            itemCount[i] = 0;
        for (Shop shop: shopList) {
            input.append("<tr>\n");
            input.append("<th>" + shop.getName() + "</th>\n");
            for (int i = 0; i < itemList.size(); i++) {
                Item item = itemList.get(i);
                input.append("<th>");
                int count = 0;
                for (Sell sell: sellList) {
                    if (sell.getShop().equals(shop) && sell.getItem().equals(item)) {
                        count = sell.getCount();
                        itemCount[i] += count;
                    }
                }
                input.append(count + "</th>\n");
            }
            input.append("</tr>\n");
        }
        input.append("<tr>\n");
        input.append("<th>Итого:</th>\n");
        for (int i = 0; i < itemCount.length; i++)
            input.append("<th>" + itemCount[i] + "</th>\n");
        input.append("</tr>\n");
        input.append("</table>\n</body>\n" +
                "</html>");
        FileOutputStream fos = new FileOutputStream(".\\src\\main\\resources\\reports\\tableReport.html");
        fos.write(input.toString().getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

}
