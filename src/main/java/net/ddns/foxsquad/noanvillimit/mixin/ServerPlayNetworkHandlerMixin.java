package net.ddns.foxsquad.noanvillimit.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements ServerPlayPacketListener {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "onRenameItem", cancellable = true)
    private void onRenameItem(RenameItemC2SPacket packet, CallbackInfo ci) {
        ci.cancel();
        NetworkThreadUtils.forceMainThread(packet, this, player.getServerWorld());
        if (this.player.currentScreenHandler instanceof AnvilScreenHandler) {
            AnvilScreenHandler anvilScreenHandler = (AnvilScreenHandler)this.player.currentScreenHandler;
            String string = SharedConstants.stripInvalidChars(packet.getName());
            anvilScreenHandler.setNewItemName(string);
        }
    }
}