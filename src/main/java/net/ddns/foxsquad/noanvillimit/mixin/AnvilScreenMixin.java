package net.ddns.foxsquad.noanvillimit.mixin;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {
    @Shadow
    private TextFieldWidget nameField;

    @Inject(at = @At("TAIL"), method = "setup")
    protected void setup(CallbackInfo ci) {
        this.nameField.setMaxLength(Integer.MAX_VALUE);
    }
}
