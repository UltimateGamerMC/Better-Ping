/*
 * External method calls:
 *   Lnet/minecraft/util/collection/DefaultedList;ofSize(ILjava/lang/Object;)Lnet/minecraft/util/collection/DefaultedList;
 *   Lnet/minecraft/block/entity/LootableContainerBlockEntity;writeData(Lnet/minecraft/storage/WriteView;)V
 *   Lnet/minecraft/inventory/Inventories;writeData(Lnet/minecraft/storage/WriteView;Lnet/minecraft/util/collection/DefaultedList;)V
 *   Lnet/minecraft/block/entity/LootableContainerBlockEntity;readData(Lnet/minecraft/storage/ReadView;)V
 *   Lnet/minecraft/inventory/Inventories;readData(Lnet/minecraft/storage/ReadView;Lnet/minecraft/util/collection/DefaultedList;)V
 *   Lnet/minecraft/screen/GenericContainerScreenHandler;createGeneric9x3(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)Lnet/minecraft/screen/GenericContainerScreenHandler;
 *   Lnet/minecraft/entity/ContainerUser;asLivingEntity()Lnet/minecraft/entity/LivingEntity;
 *   Lnet/minecraft/block/entity/ViewerCountManager;openContainer(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;D)V
 *   Lnet/minecraft/block/entity/ViewerCountManager;closeContainer(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V
 *   Lnet/minecraft/block/entity/ViewerCountManager;updateViewerCount(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V
 *   Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
 *   Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/Entity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/block/entity/BarrelBlockEntity;writeLootTable(Lnet/minecraft/storage/WriteView;)Z
 *   Lnet/minecraft/block/entity/BarrelBlockEntity;readLootTable(Lnet/minecraft/storage/ReadView;)Z
 */
package net.minecraft.block.entity;

import java.util.List;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.ContainerUser;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class BarrelBlockEntity
extends LootableContainerBlockEntity {
    private static final Text CONTAINER_NAME_TEXT = Text.translatable("container.barrel");
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
    private final ViewerCountManager stateManager = new ViewerCountManager(){

        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            BarrelBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_OPEN);
            BarrelBlockEntity.this.setOpen(state, true);
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            BarrelBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
            BarrelBlockEntity.this.setOpen(state, false);
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        }

        @Override
        public boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory lv = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                return lv == BarrelBlockEntity.this;
            }
            return false;
        }
    };

    public BarrelBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityType.BARREL, pos, state);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (!this.writeLootTable(view)) {
            Inventories.writeData(view, this.inventory);
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(view)) {
            Inventories.readData(view, this.inventory);
        }
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected Text getContainerName() {
        return CONTAINER_NAME_TEXT;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    @Override
    public void onOpen(ContainerUser user) {
        if (!this.removed && !user.asLivingEntity().isSpectator()) {
            this.stateManager.openContainer(user.asLivingEntity(), this.getWorld(), this.getPos(), this.getCachedState(), user.getContainerInteractionRange());
        }
    }

    @Override
    public void onClose(ContainerUser user) {
        if (!this.removed && !user.asLivingEntity().isSpectator()) {
            this.stateManager.closeContainer(user.asLivingEntity(), this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public List<ContainerUser> getViewingUsers() {
        return this.stateManager.getViewingUsers(this.getWorld(), this.getPos());
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    void setOpen(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), (BlockState)state.with(BarrelBlock.OPEN, open), Block.NOTIFY_ALL);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i lv = state.get(BarrelBlock.FACING).getVector();
        double d = (double)this.pos.getX() + 0.5 + (double)lv.getX() / 2.0;
        double e = (double)this.pos.getY() + 0.5 + (double)lv.getY() / 2.0;
        double f = (double)this.pos.getZ() + 0.5 + (double)lv.getZ() / 2.0;
        this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
    }
}

