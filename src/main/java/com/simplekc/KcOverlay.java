package com.simplekc;

import com.google.inject.Inject;
import javax.inject.Singleton;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.time.Duration;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.*;

@Singleton
public class KcOverlay extends Overlay
{
    private final KcPlugin plugin;
    private final PanelComponent panel = new PanelComponent();

    @Inject
    public KcOverlay(KcPlugin plugin)
    {
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D g)
    {
        panel.getChildren().clear();

        int kills = plugin.getKills();
        long elapsed = System.currentTimeMillis() - plugin.getStartTime();
        double hours = elapsed / 3_600_000d;
        int kph = hours > 0 ? (int)(kills / hours) : 0;
        long avgMs = kills > 0 ? elapsed / kills : 0;
        Duration avg = Duration.ofMillis(avgMs);
        String mmss = String.format("%02d:%02d", avg.toMinutesPart(), avg.toSecondsPart());

        panel.getChildren().add(LineComponent.builder()
                .left("Kills")
                .right(Integer.toString(kills))
                .build());
        panel.getChildren().add(LineComponent.builder()
                .left("Kills/hr")
                .right(Integer.toString(kph))
                .build());
        panel.getChildren().add(LineComponent.builder()
                .left("Time/kill")
                .right(mmss)
                .build());

        return panel.render(g);
    }
}
