package me.znepb.roadworks.datagen

import me.znepb.roadworks.Registry
import me.znepb.roadworks.RoadworksMain.NAMESPACE
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

class LanguageProvider(output: FabricDataOutput) : FabricLanguageProvider(output) {
    override fun generateTranslations(translationBuilder: TranslationBuilder) {
        translationBuilder.add(Registry.ModBlocks.THICK_POST, "Thick Post")
        translationBuilder.add(Registry.ModBlocks.POST, "Post")
        translationBuilder.add(Registry.ModBlocks.THIN_POST, "Thin Post")

        translationBuilder.add(Registry.ModBlocks.TRAFFIC_CONE, "Traffic Cone")
        translationBuilder.add(Registry.ModBlocks.CHANNELER, "Channeler")
        translationBuilder.add(Registry.ModBlocks.DRUM, "Drum")
        translationBuilder.add(Registry.ModBlocks.BOLLARD_THIN, "Thin Bollard")
        translationBuilder.add(Registry.ModBlocks.BOLLARD, "Bollard")
        translationBuilder.add(Registry.ModBlocks.BOLLARD_THICK, "Thick Bollard")

        translationBuilder.add(Registry.ModBlocks.SIGN, "Sign")

        translationBuilder.add(Registry.ModItems.LINKER, "Linker")
        translationBuilder.add(Registry.ModBlocks.TRAFFIC_CABINET, "Traffic Cabinet")
        translationBuilder.add(Registry.ModBlocks.ONE_HEAD_GREEN_TRAFFIC_SIGNAL, "Green Beacon")
        translationBuilder.add(Registry.ModBlocks.ONE_HEAD_RED_TRAFFIC_SIGNAL, "Red Beacon")
        translationBuilder.add(Registry.ModBlocks.ONE_HEAD_YELLOW_TRAFFIC_SIGNAL, "Yellow Beacon")
        translationBuilder.add(Registry.ModBlocks.THREE_HEAD_TRAFFIC_SIGNAL, "Three-head Traffic Signal")
        translationBuilder.add(Registry.ModBlocks.THREE_HEAD_TRAFFIC_SIGNAL_LEFT, "Three-head Traffic Signal Left")
        translationBuilder.add(Registry.ModBlocks.THREE_HEAD_TRAFFIC_SIGNAL_RIGHT, "Three-head Traffic Signal Right")
        translationBuilder.add(Registry.ModBlocks.THREE_HEAD_TRAFFIC_SIGNAL_STRAIGHT, "Three-head Traffic Signal Straight")
        translationBuilder.add(Registry.ModBlocks.FIVE_HEAD_TRAFFIC_SIGNAL_LEFT, "Five-head Traffic Signal Left")
        translationBuilder.add(Registry.ModBlocks.FIVE_HEAD_TRAFFIC_SIGNAL_RIGHT, "Five-head Traffic Signal Right")
        translationBuilder.add(Registry.ModBlocks.PEDESTRIAN_SIGNAL, "Pedestrian Signal")
        translationBuilder.add(Registry.ModBlocks.PEDESTRIAN_BUTTON, "Pedestrian Button")

        translationBuilder.add(Registry.ModBlocks.WHITE_INFILL_MARKING, "White Infill Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_ARROW_LEFT_MARKING, "White Left Arrow Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_ARROW_STRAIGHT_MARKING, "White Straight Arrow Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_ARROW_RIGHT_MARKING, "White Right Arrow Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_ONLY_MARKING, "Only Marking, White")
        translationBuilder.add(Registry.ModBlocks.WHITE_HOV_MARKING, "White HOV Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_RAILROAD_MARKING, "Railroad Marking, White")
        translationBuilder.add(Registry.ModBlocks.WHITE_ARROW_LEFT_STRAIGHT_MARKING, "White Left/Straight Arrows")
        translationBuilder.add(Registry.ModBlocks.WHITE_ARROW_RIGHT_STRAIGHT_MARKING, "White Right/Straight Arrows")
        translationBuilder.add(Registry.ModBlocks.WHITE_ARROW_RIGHT_LEFT_MARKING, "White Left/Right Arrows")
        translationBuilder.add(Registry.ModBlocks.WHITE_ARROW_U_TURN_MARKING, "White U-Turn Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_ZEBRA_CROSSING_MARKING, "White Zebra Crossing")

        translationBuilder.add(Registry.ModBlocks.WHITE_CENTER_DASH_MARKING, "Centered White Dash")
        translationBuilder.add(Registry.ModBlocks.WHITE_CENTER_TURN_MARKING, "Centered White Turn Line")
        translationBuilder.add(Registry.ModBlocks.WHITE_CENTER_MARKING, "Centered Long White Line")
        translationBuilder.add(Registry.ModBlocks.WHITE_CENTER_THICK, "Centered Thick White Line")
        translationBuilder.add(Registry.ModBlocks.WHITE_CENTER_STUB_SHORT, "White Centered Short Stub")
        translationBuilder.add(Registry.ModBlocks.WHITE_CENTER_STUB_MEDIUM, "White Centered Medium Stub")
        translationBuilder.add(Registry.ModBlocks.WHITE_CENTER_STUB_LONG, "White Centered Long Stub")

        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_DASH_MARKING, "Edge White Dash")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_MARKING, "Edge White Line")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_TURN_MARKING_INSIDE, "Inside White Edge Turn Line")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_TURN_MARKING_OUTSIDE, "Outside White Edge Turn Line")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_THICK, "Edge Thick White Line")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_STUB_SHORT_LEFT, "White Edge Stub Short (Left)")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_STUB_MEDIUM_LEFT, "White Edge Stub Medium (Left)")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_STUB_LONG_LEFT, "White Edge Stub Long (Left)")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_STUB_SHORT_RIGHT, "White Edge Stub Short (Right)")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_STUB_MEDIUM_RIGHT, "White Edge Stub Medium (Right)")
        translationBuilder.add(Registry.ModBlocks.WHITE_EDGE_STUB_LONG_RIGHT, "White Edge Stub Long (Right)")

        translationBuilder.add(Registry.ModBlocks.WHITE_T_CENTER_LONG, "White Long Center T Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_T_RIGHT_LONG, "White Long Right T Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_T_LEFT_LONG, "White Long Left T Marking")

        translationBuilder.add(Registry.ModBlocks.WHITE_T_CENTER, "White Center T Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_T_CENTER_LEFT, "White Center T Marking, Left Line")
        translationBuilder.add(Registry.ModBlocks.WHITE_T_CENTER_RIGHT, "White Center T Marking, Right Line")

        translationBuilder.add(Registry.ModBlocks.WHITE_T_CENTER_SHORT, "White Short Center T Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_T_SHORT_LEFT, "White Short Left T Marking")
        translationBuilder.add(Registry.ModBlocks.WHITE_T_SHORT_RIGHT, "White Short Right T Marking")

        translationBuilder.add(Registry.ModBlocks.WHITE_L_THIN_LEFT, "White Thin L Left")
        translationBuilder.add(Registry.ModBlocks.WHITE_L_THIN_RIGHT, "White Thin L Right")
        translationBuilder.add(Registry.ModBlocks.WHITE_L_LEFT, "White L Left")
        translationBuilder.add(Registry.ModBlocks.WHITE_L_RIGHT, "White L Right")
        translationBuilder.add(Registry.ModBlocks.WHITE_L_SHORT_LEFT, "White L Thin, Short Left")
        translationBuilder.add(Registry.ModBlocks.WHITE_L_SHORT_RIGHT, "White L Thin, Short Right")

        ///

        translationBuilder.add(Registry.ModBlocks.YELLOW_INFILL_MARKING, "Yellow Infill Marking")

        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_DASH_MARKING, "Centered Yellow Dash")
        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_TURN_MARKING, "Centered Yellow Turn Line")
        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_MARKING, "Centered Long Yellow Line")

        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_OFFSET, "Offset Center Yellow Line")
        translationBuilder.add(Registry.ModBlocks.YELLOW_DOUBLE, "Centered Double Yellow Line")
        translationBuilder.add(Registry.ModBlocks.YELLOW_DOUBLE_TURN, "Centered Double Yellow Turn Line")
        translationBuilder.add(Registry.ModBlocks.YELLOW_DOUBLE_SPLIT_LEFT, "Centered Double Yellow Split Left")
        translationBuilder.add(Registry.ModBlocks.YELLOW_DOUBLE_SPLIT_RIGHT, "Centered Double Yellow Split Right")
        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_OFFSET_INSIDE, "Inside Offset Yellow Turn")
        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_OFFSET_OUTSIDE, "Outside Offset Yellow Turn")
        translationBuilder.add(Registry.ModBlocks.YELLOW_OFFSET_OUTSIDE_TO_CENTER_R, "Center-Offset Outside to Center Turn (Right)")
        translationBuilder.add(Registry.ModBlocks.YELLOW_OFFSET_OUTSIDE_TO_CENTER_L, "Center-Offset Outside to Center Turn (Left)")
        translationBuilder.add(Registry.ModBlocks.YELLOW_OFFSET_INSIDE_TO_CENTER_R, "Center-Offset Inside to Center Turn (Right)")
        translationBuilder.add(Registry.ModBlocks.YELLOW_OFFSET_INSIDE_TO_CENTER_L, "Center-Offset Inside to Center Turn (Left)")

        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_STUB_SHORT, "Yellow Centered Short Stub")
        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_STUB_MEDIUM, "Yellow Centered Medium Stub")
        translationBuilder.add(Registry.ModBlocks.YELLOW_CENTER_STUB_LONG, "Yellow Centered Long Stub")

        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_DASH_MARKING, "Edge Yellow Dash")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_MARKING, "Edge Yellow Line")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_TURN_MARKING_INSIDE, "Inside Yellow Edge Turn Line")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_TURN_MARKING_OUTSIDE, "Outside Yellow Edge Turn Line")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_STUB_SHORT_LEFT, "Yellow Edge Stub Short (Left)")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_STUB_MEDIUM_LEFT, "Yellow Edge Stub Medium (Left)")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_STUB_LONG_LEFT, "Yellow Edge Stub Long (Left)")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_STUB_SHORT_RIGHT, "Yellow Edge Stub Short (Right)")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_STUB_MEDIUM_RIGHT, "Yellow Edge Stub Medium (Right)")
        translationBuilder.add(Registry.ModBlocks.YELLOW_EDGE_STUB_LONG_RIGHT, "Yellow Edge Stub Long (Right)")

        translationBuilder.add(Registry.ModBlocks.YELLOW_T_CENTER_LONG, "Yellow Long Center T Marking")
        translationBuilder.add(Registry.ModBlocks.YELLOW_T_RIGHT_LONG, "Yellow Long Right T Marking")
        translationBuilder.add(Registry.ModBlocks.YELLOW_T_LEFT_LONG, "Yellow Long Left T Marking")

        translationBuilder.add(Registry.ModBlocks.YELLOW_T_CENTER, "Yellow Center T Marking")
        translationBuilder.add(Registry.ModBlocks.YELLOW_T_CENTER_LEFT, "Yellow Center T Marking, Left Line")
        translationBuilder.add(Registry.ModBlocks.YELLOW_T_CENTER_RIGHT, "Yellow Center T Marking, Right Line")

        translationBuilder.add(Registry.ModBlocks.YELLOW_T_CENTER_SHORT, "Yellow Short Center T Marking")
        translationBuilder.add(Registry.ModBlocks.YELLOW_T_SHORT_LEFT, "Yellow Short Left T Marking")
        translationBuilder.add(Registry.ModBlocks.YELLOW_T_SHORT_RIGHT, "Yellow Short Right T Marking")

        translationBuilder.add(Registry.ModBlocks.YELLOW_L_THIN_LEFT, "Yellow Thin L Left")
        translationBuilder.add(Registry.ModBlocks.YELLOW_L_THIN_RIGHT, "Yellow Thin L Right")
        translationBuilder.add(Registry.ModBlocks.YELLOW_L_LEFT, "Yellow L Left")
        translationBuilder.add(Registry.ModBlocks.YELLOW_L_RIGHT, "Yellow L Right")
        translationBuilder.add(Registry.ModBlocks.YELLOW_L_SHORT_LEFT, "Yellow L Thin, Short Left")
        translationBuilder.add(Registry.ModBlocks.YELLOW_L_SHORT_RIGHT, "Yellow L Thin, Short Right")

        translationBuilder.add("itemGroup.${NAMESPACE}.main", "Roadworks")
        translationBuilder.add("block.${NAMESPACE}.stop_sign", "Stop Sign")
    }
}