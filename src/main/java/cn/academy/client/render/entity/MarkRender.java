package cn.academy.client.render.entity;

import cn.academy.client.render.util.SimpleModelBiped;
import cn.academy.Resources;
import cn.academy.entity.EntityTPMarking;
import cn.lambdalib2.util.RenderUtils;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

@SideOnly(Side.CLIENT)
public class MarkRender extends Render {

    {
        shadowOpaque = 0;
    }

    protected ResourceLocation[] tex = Resources.getEffectSeq("tp_mark", 7);
    protected SimpleModelBiped model = new SimpleModelBiped();

    @Override
    public void doRender(Entity ent, double x, double y, double z, float var8, float var9) {
        if (RenderUtils.isInShadowPass())
            return;

        EntityTPMarking mark = (EntityTPMarking) ent;
        if (!mark.firstUpdated())
            return;

        int texID = (int) ((mark.ticksExisted / 2.5) % tex.length);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glPushMatrix();
        {
            GL11.glTranslated(x, y, z);

            GL11.glRotated(-mark.rotationYaw, 0, 1, 0);
            GL11.glScaled(-1, -1, 1);
            ShaderSimple.instance().useProgram();
            RenderUtils.loadTexture(tex[texID]);

            if (!mark.available) {
                GL11.glColor4d(1, 0.2, 0.2, 1);
            } else {
                GL11.glColor4d(1, 1, 1, 1);
            }

            model.draw();
            GL20.glUseProgram(0);
        }
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity var1) {
        return null;
    }

}