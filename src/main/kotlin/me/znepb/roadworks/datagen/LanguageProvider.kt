package me.znepb.roadworks.datagen

import me.znepb.roadworks.RoadworksMain.NAMESPACE
import me.znepb.roadworks.RoadworksRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

class LanguageProvider(output: FabricDataOutput) : FabricLanguageProvider(output) {
    override fun generateTranslations(translationBuilder: TranslationBuilder) {

        translationBuilder.add(RoadworksRegistry.ModBlocks.TRAFFIC_CONE, "Traffic Cone")
        translationBuilder.add(RoadworksRegistry.ModBlocks.CHANNELER, "Channeler")
        translationBuilder.add(RoadworksRegistry.ModBlocks.DRUM, "Drum")
        translationBuilder.add(RoadworksRegistry.ModBlocks.BOLLARD_THIN, "Thin Bollard")
        translationBuilder.add(RoadworksRegistry.ModBlocks.BOLLARD, "Bollard")
        translationBuilder.add(RoadworksRegistry.ModBlocks.BOLLARD_THICK, "Thick Bollard")

        translationBuilder.add(RoadworksRegistry.ModItems.ROAD_SIGN_ATTACHMENT, "Road Sign")
        translationBuilder.add(RoadworksRegistry.ModItems.ROAD_SIGN_WARNING_ATTACHMENT, "Warning Road Sign")
        translationBuilder.add(RoadworksRegistry.ModItems.SIGN_ATTACHMENT, "Sign")

        translationBuilder.add(RoadworksRegistry.ModItems.WRENCH, "Wrench")
        translationBuilder.add(RoadworksRegistry.ModItems.LINKER, "Linker")
        translationBuilder.add(RoadworksRegistry.ModItems.SIGN_EDITOR, "Sign Editor")

        translationBuilder.add(RoadworksRegistry.ModBlocks.TRAFFIC_CABINET, "Traffic Cabinet")
        translationBuilder.add(RoadworksRegistry.ModItems.BEACON_ATTACHMENT_GREEN, "Green Beacon")
        translationBuilder.add(RoadworksRegistry.ModItems.BEACON_ATTACHMENT_RED, "Red Beacon")
        translationBuilder.add(RoadworksRegistry.ModItems.BEACON_ATTACHMENT_YELLOW, "Yellow Beacon")
        translationBuilder.add(RoadworksRegistry.ModItems.THREE_HEAD_TRAFFIC_SIGNAL_ATTACHMENT, "Three-head Traffic Signal")
        translationBuilder.add(RoadworksRegistry.ModItems.THREE_HEAD_TRAFFIC_SIGNAL_ATTACHMENT_LEFT, "Three-head Traffic Signal Left")
        translationBuilder.add(RoadworksRegistry.ModItems.THREE_HEAD_TRAFFIC_SIGNAL_ATTACHMENT_RIGHT, "Three-head Traffic Signal Right")
        translationBuilder.add(RoadworksRegistry.ModItems.THREE_HEAD_TRAFFIC_SIGNAL_ATTACHMENT_STRAIGHT, "Three-head Traffic Signal Straight")
        translationBuilder.add(RoadworksRegistry.ModItems.FIVE_HEAD_TRAFFIC_SIGNAL_ATTACHMENT_LEFT, "Five-head Traffic Signal Left")
        translationBuilder.add(RoadworksRegistry.ModItems.FIVE_HEAD_TRAFFIC_SIGNAL_ATTACHMENT_RIGHT, "Five-head Traffic Signal Right")
        translationBuilder.add(RoadworksRegistry.ModItems.FIVE_HEAD_TRAFFIC_SIGNAL_ATTACHMENT_LEFT_RIGHT, "Five-head Traffic Signal Left/Right")
        translationBuilder.add(RoadworksRegistry.ModItems.PEDESTRIAN_SIGNAL_ATTACHMENT, "Pedestrian Signal")
        // translationBuilder.add(RoadworksRegistry.ModBlocks.PEDESTRIAN_BUTTON, "Pedestrian Button")

        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_INFILL_MARKING, "White Infill Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ARROW_LEFT_MARKING, "White Left Arrow Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ARROW_STRAIGHT_MARKING, "White Straight Arrow Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ARROW_RIGHT_MARKING, "White Right Arrow Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ONLY_MARKING, "Only Marking, White")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_HOV_MARKING, "White HOV Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_RAILROAD_MARKING, "Railroad Marking, White")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ARROW_LEFT_STRAIGHT_MARKING, "White Left/Straight Arrows")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ARROW_RIGHT_STRAIGHT_MARKING, "White Right/Straight Arrows")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ARROW_RIGHT_LEFT_MARKING, "White Left/Right Arrows")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ARROW_U_TURN_MARKING, "White U-Turn Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_ZEBRA_CROSSING_MARKING, "White Zebra Crossing")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_YIELD_MARKING, "White Yield Marking")

        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_CENTER_DASH_MARKING, "Centered White Dash")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_CENTER_TURN_MARKING, "Centered White Turn Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_CENTER_MARKING, "Centered Long White Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_CENTER_THICK, "Centered Thick White Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_CENTER_STUB_SHORT, "White Centered Short Stub")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_CENTER_STUB_MEDIUM, "White Centered Medium Stub")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_CENTER_STUB_LONG, "White Centered Long Stub")

        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_DASH_MARKING, "Edge White Dash")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_MARKING, "Edge White Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_TURN_MARKING_INSIDE, "Inside White Edge Turn Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_TURN_MARKING_OUTSIDE, "Outside White Edge Turn Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_THICK, "Edge Thick White Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_STUB_SHORT_LEFT, "White Edge Stub Short (Left)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_STUB_MEDIUM_LEFT, "White Edge Stub Medium (Left)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_STUB_LONG_LEFT, "White Edge Stub Long (Left)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_STUB_SHORT_RIGHT, "White Edge Stub Short (Right)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_STUB_MEDIUM_RIGHT, "White Edge Stub Medium (Right)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_EDGE_STUB_LONG_RIGHT, "White Edge Stub Long (Right)")

        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_CENTER_LONG, "White Long Center T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_RIGHT_LONG, "White Long Right T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_LEFT_LONG, "White Long Left T Marking")

        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_CENTER, "White Center T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_CENTER_LEFT, "White Center T Marking, Left Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_CENTER_RIGHT, "White Center T Marking, Right Line")

        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_CENTER_SHORT, "White Short Center T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_SHORT_LEFT, "White Short Left T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_T_SHORT_RIGHT, "White Short Right T Marking")

        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_L_THIN_LEFT, "White Thin L Left")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_L_THIN_RIGHT, "White Thin L Right")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_L_LEFT, "White L Left")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_L_RIGHT, "White L Right")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_L_SHORT_LEFT, "White L Thin, Short Left")
        translationBuilder.add(RoadworksRegistry.ModBlocks.WHITE_L_SHORT_RIGHT, "White L Thin, Short Right")

        ///

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_INFILL_MARKING, "Yellow Infill Marking")

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_DASH_MARKING, "Centered Yellow Dash")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_TURN_MARKING, "Centered Yellow Turn Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_MARKING, "Centered Long Yellow Line")

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_OFFSET, "Offset Center Yellow Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_DOUBLE, "Centered Double Yellow Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_DOUBLE_TURN, "Centered Double Yellow Turn Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_DOUBLE_SPLIT_LEFT, "Centered Double Yellow Split Left")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_DOUBLE_SPLIT_RIGHT, "Centered Double Yellow Split Right")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_OFFSET_INSIDE, "Inside Offset Yellow Turn")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_OFFSET_OUTSIDE, "Outside Offset Yellow Turn")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_OFFSET_OUTSIDE_TO_CENTER_R, "Center-Offset Outside to Center Turn (Right)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_OFFSET_OUTSIDE_TO_CENTER_L, "Center-Offset Outside to Center Turn (Left)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_OFFSET_INSIDE_TO_CENTER_R, "Center-Offset Inside to Center Turn (Right)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_OFFSET_INSIDE_TO_CENTER_L, "Center-Offset Inside to Center Turn (Left)")

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_STUB_SHORT, "Yellow Centered Short Stub")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_STUB_MEDIUM, "Yellow Centered Medium Stub")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_CENTER_STUB_LONG, "Yellow Centered Long Stub")

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_DASH_MARKING, "Edge Yellow Dash")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_MARKING, "Edge Yellow Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_TURN_MARKING_INSIDE, "Inside Yellow Edge Turn Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_TURN_MARKING_OUTSIDE, "Outside Yellow Edge Turn Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_STUB_SHORT_LEFT, "Yellow Edge Stub Short (Left)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_STUB_MEDIUM_LEFT, "Yellow Edge Stub Medium (Left)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_STUB_LONG_LEFT, "Yellow Edge Stub Long (Left)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_STUB_SHORT_RIGHT, "Yellow Edge Stub Short (Right)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_STUB_MEDIUM_RIGHT, "Yellow Edge Stub Medium (Right)")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_EDGE_STUB_LONG_RIGHT, "Yellow Edge Stub Long (Right)")

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_CENTER_LONG, "Yellow Long Center T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_RIGHT_LONG, "Yellow Long Right T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_LEFT_LONG, "Yellow Long Left T Marking")

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_CENTER, "Yellow Center T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_CENTER_LEFT, "Yellow Center T Marking, Left Line")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_CENTER_RIGHT, "Yellow Center T Marking, Right Line")

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_CENTER_SHORT, "Yellow Short Center T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_SHORT_LEFT, "Yellow Short Left T Marking")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_T_SHORT_RIGHT, "Yellow Short Right T Marking")

        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_L_THIN_LEFT, "Yellow Thin L Left")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_L_THIN_RIGHT, "Yellow Thin L Right")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_L_LEFT, "Yellow L Left")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_L_RIGHT, "Yellow L Right")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_L_SHORT_LEFT, "Yellow L Thin, Short Left")
        translationBuilder.add(RoadworksRegistry.ModBlocks.YELLOW_L_SHORT_RIGHT, "Yellow L Thin, Short Right")

        translationBuilder.add("gui.${NAMESPACE}.sign_editor.name", "Sign Editor")
        translationBuilder.add("gui.${NAMESPACE}.sign_editor.set", "Set")

        translationBuilder.add("itemGroup.${NAMESPACE}.main", "Roadworks")
        translationBuilder.add("sign.${NAMESPACE}.stop_sign", "Stop Sign")
        translationBuilder.add("sign.${NAMESPACE}.3_way_intersection", "3-Way Intersection Sign Right")
        translationBuilder.add("sign.${NAMESPACE}.3_way_intersection_alt", "3-Way Intersection Sign Left")
        translationBuilder.add("sign.${NAMESPACE}.3_way_intersection_t", "3-Way Intersection Sign T")
        translationBuilder.add("sign.${NAMESPACE}.4_way_intersection", "4-Way Intersection Sign")
        translationBuilder.add("sign.${NAMESPACE}.chevron_left", "Chevron Left Sign")
        translationBuilder.add("sign.${NAMESPACE}.chevron_right", "Chevron Right Sign")
        translationBuilder.add("sign.${NAMESPACE}.do_not_enter", "Do Not Enter Sign")
        translationBuilder.add("sign.${NAMESPACE}.hard_left", "Hard Left Sign")
        translationBuilder.add("sign.${NAMESPACE}.hard_right", "Hard Right Sign")
        translationBuilder.add("sign.${NAMESPACE}.left_lane_ends", "Left Lane Ends Sign")
        translationBuilder.add("sign.${NAMESPACE}.left_lane_ends_construction", "Left Lane Ends Construction Sign")
        translationBuilder.add("sign.${NAMESPACE}.left_only", "Left Only Sign")
        translationBuilder.add("sign.${NAMESPACE}.one_way_left", "One Way Sign (Left)")
        translationBuilder.add("sign.${NAMESPACE}.one_way_right", "One Way Sign (Right)")
        translationBuilder.add("sign.${NAMESPACE}.pedestrian", "Pedestrian Sign")
        translationBuilder.add("sign.${NAMESPACE}.railroad", "Railroad Crossing Sign")
        translationBuilder.add("sign.${NAMESPACE}.right_lane_ends", "Right Lane Ends Sign")
        translationBuilder.add("sign.${NAMESPACE}.right_lane_ends_construction", "Right Lane Ends Construction Sign")
        translationBuilder.add("sign.${NAMESPACE}.right_only", "Right Only Sign")
        translationBuilder.add("sign.${NAMESPACE}.road_closed", "Road Closed Sign")
        translationBuilder.add("sign.${NAMESPACE}.road_closed_ahead", "Road Closed Ahead Sign")
        translationBuilder.add("sign.${NAMESPACE}.road_work_ahead", "Road Work Ahead Sign")
        translationBuilder.add("sign.${NAMESPACE}.roundabout", "Roundabout Sign")
        translationBuilder.add("sign.${NAMESPACE}.roundabout_ahead", "Roundabout Ahead Sign")
        translationBuilder.add("sign.${NAMESPACE}.signal_ahead", "Signal Ahead Sign")
        translationBuilder.add("sign.${NAMESPACE}.speed_limit_5", "Speed Limit 5 Sign")
        translationBuilder.add("sign.${NAMESPACE}.speed_limit_10", "Speed Limit 10 Sign")
        translationBuilder.add("sign.${NAMESPACE}.speed_limit_15", "Speed Limit 15 Sign")
        translationBuilder.add("sign.${NAMESPACE}.speed_limit_20", "Speed Limit 20 Sign")
        translationBuilder.add("sign.${NAMESPACE}.speed_limit_25", "Speed Limit 25 Sign")
        translationBuilder.add("sign.${NAMESPACE}.speed_limit_30", "Speed Limit 30 Sign")
        translationBuilder.add("sign.${NAMESPACE}.stop_ahead", "Stop Ahead Sign")
        translationBuilder.add("sign.${NAMESPACE}.straight_left", "Straight Left Sign")
        translationBuilder.add("sign.${NAMESPACE}.straight_only", "Straight Only Sign")
        translationBuilder.add("sign.${NAMESPACE}.straight_right", "Straight Right Sign")
        translationBuilder.add("sign.${NAMESPACE}.supplemental_ahead", "Supplemental Ahead Sign")
        translationBuilder.add("sign.${NAMESPACE}.supplemental_all_way", "Supplemental All-Way Sign")
        translationBuilder.add("sign.${NAMESPACE}.supplemental_diag_black_arrow_left", "Supplemental Diagonal Arrow Sign (Left)")
        translationBuilder.add("sign.${NAMESPACE}.supplemental_diag_black_arrow_right", "Supplemental Diagonal Arrow Sign (Right)")
        translationBuilder.add("sign.${NAMESPACE}.yield", "Yield Sign")
        translationBuilder.add("sign.${NAMESPACE}.yield_ahead", "Yield Ahead Sign")
        translationBuilder.add("sign.${NAMESPACE}.wrong_way", "Wrong Way Sign")

        translationBuilder.add("block.${NAMESPACE}.post_thick", "Thick Post")
        translationBuilder.add("block.${NAMESPACE}.post_medium", "Post")
        translationBuilder.add("block.${NAMESPACE}.post_thin", "Thin Post")
    }
}