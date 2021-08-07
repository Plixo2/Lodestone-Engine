package net.plixo.lodestone.client.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.plixo.lodestone.Lodestone;
import net.plixo.lodestone.client.manager.RAssets;
import net.plixo.lodestone.client.manager.RFunctions;
import net.plixo.lodestone.client.ui.screens.ScreenCanvasUI;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.canvas.UIPages;
import net.plixo.lodestone.client.ui.elements.other.UIMouseMenu;
import net.plixo.lodestone.client.ui.other.UIToolbar;
import net.plixo.lodestone.client.ui.tabs.UIConsole;
import net.plixo.lodestone.client.ui.tabs.UIFiles;
import net.plixo.lodestone.client.ui.tabs.UIHierarchy;
import net.plixo.lodestone.client.ui.tabs.UIInspector;
import net.plixo.lodestone.client.ui.other.UISearchbar;
import net.plixo.lodestone.client.ui.tabs.UIViewport;
import net.plixo.lodestone.client.util.CursorObject;
import net.plixo.lodestone.client.visualscript.Function;

public class ScreenMain extends ScreenCanvasUI {

    public static ScreenMain instance;
    public float globalMouseX ,globalMouseY;

    UIMouseMenu menu;
    UICanvas menuHolder = new UICanvas();

    UIToolbar toolbar;
    public UISearchbar searchbar;

    public CursorObject<?> cursorObject = new CursorObject<>(null);


    public ScreenMain() {
        instance = this;
    }

    @Override
    public void tick() {
        Minecraft.getInstance().getProfiler().startSection("Lodestone Screen Tick");
        super.tick();
        Minecraft.getInstance().getProfiler().endSection();
    }

    @Override
    protected void init() {
        super.init();
        try {
            /* hierarchy */
            UIPages hierarchy = new UIPages();
            hierarchy.setDimensions(0, 0, 150, height - 10);

            UIHierarchy uiHierarchy = new UIHierarchy() {
                @Override
                public void drawScreen(float mouseX, float mouseY) {
                    mc.getProfiler().startSection("Hierarchy");
                    super.drawScreen(mouseX, mouseY);
                   mc.getProfiler().endSection();
                }
            };
            hierarchy.add(uiHierarchy);

            /* Viewport */
            UIPages viewport = new UIPages();
            viewport.setDimensions(150, 0, width - 350, height - 160);

            UIViewport uiViewport = new UIViewport() {
                @Override
                public void drawScreen(float mouseX, float mouseY) {
                      mc.getProfiler().startSection("Viewport");
                    super.drawScreen(mouseX, mouseY);
                    mc.getProfiler().endSection();
                }
            };
            viewport.add(uiViewport);




            /* console */
            UIPages console = new UIPages();
            console.setDimensions(150, height - 160, width - 350, 150);

            UIFiles uiFiles = new UIFiles() {
                @Override
                public void drawScreen(float mouseX, float mouseY) {
                    mc.getProfiler().startSection("Files");
                    super.drawScreen(mouseX, mouseY);
                    mc.getProfiler().endSection();
                }
            };
            console.add(uiFiles);

            UIConsole uiConsole = new UIConsole() {
                @Override
                public void drawScreen(float mouseX, float mouseY) {
                    mc.getProfiler().startSection("Console");
                    super.drawScreen(mouseX, mouseY);
                    mc.getProfiler().endSection();
                }
            };
            console.add(uiConsole);

            /* Inspector */
            UIPages inspector = new UIPages();
            inspector.setDimensions(width - 200, 0, 200, height - 10);

            UIInspector uiInspector = new UIInspector() {
                @Override
                public void drawScreen(float mouseX, float mouseY) {
                    mc.getProfiler().startSection("Inspector");
                    super.drawScreen(mouseX, mouseY);
                    mc.getProfiler().endSection();
                }
            };
            inspector.add(uiInspector);

            add(viewport);
            add(console);
            add(inspector);
            add(hierarchy);



            add(menuHolder);

            toolbar = new UIToolbar() {
                @Override
                public void drawScreen(float mouseX, float mouseY) {
                    mc.getProfiler().startSection("Toolbar");
                    super.drawScreen(mouseX, mouseY);
                    mc.getProfiler().endSection();
                }
            };
            toolbar.setDimensions(0, height - 10, width, 10);

            searchbar = new UISearchbar() {
                @Override
                public void drawScreen(float mouseX, float mouseY) {
                    mc.getProfiler().startSection("Searchbar");
                    super.drawScreen(mouseX, mouseY);
                    mc.getProfiler().endSection();
                }
            };
            searchbar.setDimensions(viewport.x, UIPages.headHeight, viewport.width, 10);
            searchbar.setFilteredRunnable((txt) -> {
                for (Function function : RFunctions.getFromSuperclass(RAssets.getLoadedScript().getObject())) {
                    if (function.getIDString().toUpperCase().contains(txt.toUpperCase())) {
                        searchbar.addFilteredOption(function.getDisplayName(), () -> {
                            if (RAssets.getLoadedScript() != null) {
                                    RAssets.getLoadedScript().addFunction(function,0,0);
                            }
                        });
                    }
                }
            });

            add(toolbar);
            add(searchbar);

            init(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(UICanvas canvas) {
        for (UIElement element : canvas.elements) {
            element.init();
            if (element instanceof UICanvas) {
                init((UICanvas) element);
            }
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        boolean b = super.mouseReleased(mouseX, mouseY, state);
        cursorObject = new CursorObject<>(null);
        return b;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        try {
            this.globalMouseX = (float) mouseX;
            this.globalMouseY = (float) mouseY;
            if (menu != null) {
                menu.mouseClicked(this.globalMouseX, this.globalMouseY, mouseButton);
                hideMenu();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onClose() {
        try {
            Lodestone.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onClose();
    }


    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {

        globalMouseX = mouseX;
        globalMouseY = mouseY;

        Minecraft.getInstance().getProfiler().startSection("Lodestone Screen Render");
        super.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
        Minecraft.getInstance().getProfiler().endSection();


    }

    public void hideMenu() {
        menuHolder.clear();
        menu = null;
    }

    public void showMenu() {
        menu.build(globalMouseX, globalMouseY);
        menuHolder.clear();
        menuHolder.add(menu);
    }
    public void showMenu(float mouseX , float mouseY) {
        menu.build(mouseX, mouseY);
        menuHolder.clear();
        menuHolder.add(menu);
    }

    public void beginMenu() {
        menu = new UIMouseMenu();
        menuHolder.clear();
    }

    public void addMenuOption(String group,String name, Runnable runnable) {
        menu.addOption(group,name, runnable);
    }

    public void addMenuOption(String name, Runnable runnable) {
        menu.addOption(name,name, runnable);
    }
}
