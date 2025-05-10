package com.abasing.repairmacro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Keyboard;

import java.util.LinkedList;
import java.util.Queue;

public class KeyHandler {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final KeyBinding repairKey;
    private final Queue<String> commandQueue = new LinkedList<>();
    private int tickDelay = 0;
    private boolean inCombat = false;

    public KeyHandler() {
        repairKey = new KeyBinding("Repair & Stack Macro", Keyboard.KEY_R, "Abasing Utilities");
        ClientRegistry.registerKeyBinding(repairKey);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (event.type != 0 || event.message == null) return;

        String msg = event.message.getUnformattedText().toLowerCase();

        if (msg.contains("you are now in combat")) {
            inCombat = true;
        } else if (msg.contains("you are not longer in combat")) {
            inCombat = false;
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (repairKey.isPressed() && mc.thePlayer != null) {
            commandQueue.clear();
            if (!inCombat) commandQueue.add("/repair all");
            commandQueue.add("/stack");
            commandQueue.add("/repair hand");
            tickDelay = 1; // Start tick delay
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || mc.thePlayer == null || commandQueue.isEmpty()) return;

        if (tickDelay <= 0) {
            String command = commandQueue.poll();
            if (command != null) {
                mc.thePlayer.sendChatMessage(command);
            }
            tickDelay = 1;
        } else {
            tickDelay--;
        }
    }
}
