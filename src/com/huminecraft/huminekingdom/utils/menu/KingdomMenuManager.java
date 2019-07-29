package com.huminecraft.huminekingdom.utils.menu;

import com.huminecraft.humineaypi.utils.Button;
import com.huminecraft.humineaypi.utils.Menu;
import com.huminecraft.huminekingdom.HumineKingdom;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonAction;
import com.huminecraft.huminekingdom.utils.menu.menuaction.ButtonActionManager;
import com.huminecraft.huminekingdom.utils.messages.variable.ParseListError;
import com.huminecraft.huminekingdom.utils.messages.variable.Variable;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class KingdomMenuManager {

    private ArrayList<KingdomMenu> kingdomMenus;
    private static ButtonActionManager buttonActionManager;

    public KingdomMenuManager() {
        buttonActionManager = new ButtonActionManager();
        kingdomMenus = new ArrayList<KingdomMenu>();
        createMenus(new File("plugins/HumineKingdom/menus").listFiles());
    }

    private KingdomMenu parseMenu(String json) throws ParseException {
        Object obj = new JSONParser().parse(json);
        JSONObject menuJson = (JSONObject) obj;

        String id = (String) menuJson.get("id");

        int size = (menuJson.get("size") != null) ? Integer.parseInt((String) menuJson.get("size")) : 1;
        boolean security = Boolean.parseBoolean((String) menuJson.get("security"));

        KingdomMenu kingdomMenu = new KingdomMenu(id, "", size, security) {

            @Override
            protected void fillMenu(Menu menu) {
                String title = HumineKingdom.getVariableManager().getPlayerVariable(menu.getPlayer()).parseText((String) menuJson.get("title"));

                menu.setTitle(title);

                JSONArray fillLine = (JSONArray) menuJson.get("fill-line");

                if (fillLine != null) {
                    ItemStack[] items = new ItemStack[fillLine.size()];
                    for (int i = 0; i < fillLine.size(); i++) {
                        JSONObject item = (JSONObject) fillLine.get(i);
                        ItemStack itemStack = parseItem(menu.getPlayer(), item);
                        items[i] = itemStack;
                    }

                    for (int i = 0; i < fillLine.size(); i++) {
                        menu.fillLine(items[i], i + 1);
                    }
                }


                JSONArray fillItem = (JSONArray) menuJson.get("fill-item");
                if (fillItem != null) {
                    for (int i = 0; i < fillItem.size(); i++) {
                        JSONObject item = (JSONObject) fillItem.get(i);
                        parseElement(menu, item, true);
                    }
                }


                if (menuJson.get("container") != null) {
                    JSONObject container = (JSONObject) menuJson.get("container");
                    ArrayList<String> list = null;

                    try {
                        list = HumineKingdom.getVariableManager().getPlayerVariable(menu.getPlayer()).parseList((String) container.get("for"));
                    } catch (ParseListError e) {
                        e.printStackTrace();
                    }

                    int size = list.size() / 9 + 2;

                    if (!(this.getSize() == size)) {
                        this.setSize(size);
                        fillMenu(menu);
                        return;
                    }

                    int index = 0;
                    for (String string : list) {
                        JSONObject item = (JSONObject) container.get("item");
                        JSONObject button = (JSONObject) container.get("button");

                        HumineKingdom.getVariableManager().getPlayerVariable(menu.getPlayer()).setVariables(new Variable("ITEM_VALUE", string));

                        if (item != null)
                            menu.setItem(index++, parseItem(menu.getPlayer(), item));
                        else
                            menu.setButton(index++, parseButton(menu, button));
                    }

                    if (container.get("fill") != null) {
                        JSONObject fill = (JSONObject) container.get("fill");

                        menu.fillLine( parseItem( menu.getPlayer(), (JSONObject) fill.get("line") ), menu.getSize() / 9 );

                        fillItem = (JSONArray) fill.get("fill-item");
                        if (fillItem != null) {
                            for (int i = 0; i < fillItem.size(); i++) {
                                JSONObject item = (JSONObject) fillItem.get(i);
                                parseElement(menu, item, false);
                            }
                        }
                    }

                }
            }
        };
        
        return kingdomMenu; 
    }

    private KingdomMenu parseMenu(File file) throws ParseException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "UTF-8"));
        String line;
        String json = "";
        while ((line = br.readLine()) != null) {
            json+=line;
        }
        br.close();
        return parseMenu(json);
    }

    private ItemStack parseItem(Player player, JSONObject json) {

        String material = HumineKingdom.getVariableManager().getPlayerVariable(player).parseText((String) json.get("material"));
        String name = HumineKingdom.getVariableManager().getPlayerVariable(player).parseText((String) json.get("name"));

        ArrayList<String> lore = new ArrayList<String>();
        if (json.get("lore") != null) {
            JSONArray loreJSON = (JSONArray) json.get("lore");

            for (int i = 0; i < loreJSON.size(); i++) {
                lore.add(HumineKingdom.getVariableManager().getPlayerVariable(player).parseText((String) loreJSON.get(i)));
            }
        }

        if (json.get("description") != null) {
            lore = (HumineKingdom.getVariableManager().getPlayerVariable(player).parseDescription((String) json.get("description")));
        }

        ItemStack itemStack = null;
        if (material != null) {
            itemStack = new ItemStack(Material.matchMaterial(material));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        } else if (json.get("owner") != null) {
            itemStack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(HumineKingdom.getVariableManager().getPlayerVariable(player).parseText((String) json.get("owner")));
            skullMeta.setDisplayName(name);
            skullMeta.setLore(lore);
            itemStack.setItemMeta(skullMeta);
        }

        return itemStack;
    }

    private void parseElement(Menu menu, JSONObject json, boolean fix) {
        int slot = (fix) ? Integer.parseInt((String) json.get("slot")) : menu.getSize() - 9 + Integer.parseInt((String) json.get("slot"));
        if (json.get("button") != null) {
            menu.setButton(slot, parseButton(menu, (JSONObject) json.get("button")));
        } else if (json.get("item") != null) {
            menu.setItem(slot, parseItem(menu.getPlayer(), (JSONObject) json.get("item")));
        }
    }

    private Button parseButton(Menu menu, JSONObject json) {
        ItemStack item = parseItem(menu.getPlayer(), json);
        ButtonAction action = getButtonAction((String) json.get("action"));
        String redirect = ((String) json.get("redirect-to"));

        return new Button(item, () -> {
            if (action != null)
                action.getButtonActionHandler().handler(menu, item);
            if (redirect != null) {
                menu.closePlayerMenu();
                this.getKingdomMenu(redirect).openMenu(menu.getPlayer());
            }
        });
    }

    public static ButtonAction getButtonAction(String name) {
        for (ButtonAction ba : buttonActionManager.getActions()) {
            if (ba.getName().equals(name)) {
                return ba;
            }
        }
        return null;
    }

    public KingdomMenu getKingdomMenu(String id) {
        for (KingdomMenu kingdomMenu : kingdomMenus) {
            if (kingdomMenu.getId().equals(id)) {
                return kingdomMenu;
            }
        }
        return null;
    }

    private void createMenus(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                createMenus(file.listFiles());
            } else {
                try {
                    kingdomMenus.add(parseMenu(file));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
