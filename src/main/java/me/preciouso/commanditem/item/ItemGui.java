package me.preciouso.commanditem.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemGui extends Screen {
    private static final ITextComponent title = new TranslationTextComponent("commandgui.title");
    private static final ITextComponent command = new TranslationTextComponent("commandgui.command_label");

    protected TextFieldWidget commandTextField;

    protected Button doneButton;
    protected Button cancelButton;

    ItemStack itemStack;
    CommandItem item;

    private CommandSuggestionHelper suggestionHelper;

    public ItemGui(ItemStack itemStack) {
        super(ItemGui.title);
        this.itemStack = itemStack;

        if (this.itemStack.getItem() instanceof CommandItem) {
            this.item = (CommandItem) this.itemStack.getItem();
        } else {
            throw new IllegalArgumentException("This class requires a CommandItem");
        }
    }

    @Override
    public void tick() {
        // Allows typing to flow well
        this.commandTextField.tick();
    }

    @Override
    public void onClose() {
        // Idk the other GUIs had this
        Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
    }

    @Override
    protected void init() {
        // Create fields
        Minecraft.getInstance().keyboardListener.enableRepeatEvents(true);
        this.doneButton = this.addButton(new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, DialogTexts.GUI_DONE, (p_214187_1_) -> {
            this.saveAndClose();
        }));
        this.cancelButton = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, DialogTexts.GUI_CANCEL, (p_214186_1_) -> {
            this.closeScreen();
        }));

        this.commandTextField = new TextFieldWidget(this.font, this.width / 2 - 150, 50, 300, 20, new TranslationTextComponent("commandgui.command_label")) {
            protected IFormattableTextComponent getNarrationMessage() {
                return super.getNarrationMessage().appendString(ItemGui.this.suggestionHelper.getSuggestionMessage());
            }
        };

        this.commandTextField.setMaxStringLength(32500);
        this.commandTextField.setResponder(this::initCommandHelper);
        this.children.add(this.commandTextField);

        this.setFocusedDefault(this.commandTextField);

        this.suggestionHelper = new CommandSuggestionHelper(Minecraft.getInstance(), this, this.commandTextField, this.font, true, true, 0, 7, false, Integer.MIN_VALUE);
        this.suggestionHelper.shouldAutoSuggest(true);
        this.suggestionHelper.init();

        this.commandTextField.setText(this.item.getCommand(this.itemStack));
        this.doneButton.active = true;
        this.cancelButton.active = true;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render fields
        // this.renderBackground(matrixStack);

        drawCenteredString(matrixStack, this.font, title, this.width / 2, 20, 16777215);
        drawString(matrixStack, this.font, command, this.width / 2 - 150, 40, 10526880);

        addButton(doneButton);
        addButton(cancelButton);
        this.commandTextField.render(matrixStack, mouseX, mouseY, partialTicks);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.suggestionHelper.drawSuggestionList(matrixStack, mouseX, mouseY);
    }

    private void saveAndClose() {
        this.item.setCommand(this.itemStack, this.commandTextField.getText());
        this.closeScreen();
    }

    @Override
    public void closeScreen() {
        Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
        super.closeScreen();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Copied from CommandBlockGui
        if (this.suggestionHelper.onKeyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (super.keyPressed(keyCode, scanCode, modifiers)) {
            if (keyCode == 256 && this.shouldCloseOnEsc()) {
                this.closeScreen();
            }
            return true;
        } else if (keyCode != 257 && keyCode != 335) {
            return false;
        } else {
            // Press enter
            this.saveAndClose();
            return true;
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        return this.suggestionHelper.onScroll(delta) || super.mouseScrolled(mouseX, mouseY, delta);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.suggestionHelper.onClick(mouseX, mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
    }

    private void initCommandHelper(String p_214185_1_) {
        this.suggestionHelper.init();
    }
}
