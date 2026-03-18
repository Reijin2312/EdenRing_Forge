package paulevs.edenring.client.entities.models;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import paulevs.edenring.entities.LilWormEntity;

public class LilWormEntityModel extends HierarchicalModel<LilWormEntity> {
	private final ModelPart root;

	public LilWormEntityModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition root = meshDefinition.getRoot();
		PartDefinition model = root.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		model.addOrReplaceChild(
			"segment_4",
			CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -3.0F, 1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.ZERO
		);
		model.addOrReplaceChild(
			"segment_5",
			CubeListBuilder.create().texOffs(14, 8).addBox(-1.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.ZERO
		);
		model.addOrReplaceChild(
			"segment_3",
			CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
			PartPose.ZERO
		);
		model.addOrReplaceChild(
			"segment_1",
			CubeListBuilder.create().texOffs(14, 12).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.ZERO
		);
		model.addOrReplaceChild(
			"segment_2",
			CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.ZERO
		);

		return LayerDefinition.create(meshDefinition, 32, 32);
	}

	@Override
	public void setupAnim(LilWormEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.moveAnimationState, LilWormEntityAnimations.WORM_MOVE, ageInTicks, 1.0F);
		this.animate(entity.deathAnimationState, LilWormEntityAnimations.DEATH, ageInTicks, 1.0F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
